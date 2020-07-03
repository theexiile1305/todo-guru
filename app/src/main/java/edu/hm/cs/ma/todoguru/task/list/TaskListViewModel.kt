package edu.hm.cs.ma.todoguru.task.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TaskListViewModel(
    private val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: TaskDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                return TaskListViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    val tasks = database.getAllTask()

    private var _markTaskDoneEvent = MutableLiveData(false)
    val markTaskDoneEvent: LiveData<Boolean>
        get() = _markTaskDoneEvent

    private var _deleteTaskEvent = MutableLiveData(false)
    val deleteTaskEvent: LiveData<Boolean>
        get() = _deleteTaskEvent

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun markTasksAsDone(tasks: List<Task>) {
        Timber.d("Mark the task as done")
        uiScope.launch {
            tasks.forEach {
                it.done = true
                update(it)
            }
        }.invokeOnCompletion {
            _markTaskDoneEvent.value = true
        }
    }

    fun deleteTasks(tasks: List<Task>) {
        Timber.d("Delete tasks")
        uiScope.launch {
            tasks.forEach {
                delete(it)
            }
        }.invokeOnCompletion {
            _deleteTaskEvent.value = true
        }
    }

    private suspend fun update(task: Task) {
        Timber.d("Update tasks")
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun delete(task: Task) {
        Timber.d("Delete task")
        withContext(Dispatchers.IO) {
            database.delete(task)
        }
    }
}
