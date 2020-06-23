package edu.hm.cs.ma.todoguru.task.subTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.SubTask
import edu.hm.cs.ma.todoguru.database.SubTaskDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubTaskListViewModel(
    private val database: SubTaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: SubTaskDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SubTaskListViewModel::class.java)) {
                return SubTaskListViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    val subTasks = database.getAllSubTask()

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun insertSubTask(description: String) {
        uiScope.launch {
            insert(SubTask(description))
        }
    }

    fun deleteSubTask(subTask: SubTask) {
        uiScope.launch {
            delete(subTask)
        }
    }

    private suspend fun insert(subTask: SubTask) {
        withContext(Dispatchers.IO) {
            database.insert(subTask)
        }
    }

    private suspend fun delete(subTask: SubTask) {
        withContext(Dispatchers.IO) {
            database.delete(subTask)
        }
    }
}
