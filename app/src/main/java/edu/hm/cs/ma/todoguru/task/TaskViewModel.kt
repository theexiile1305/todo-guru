package edu.hm.cs.ma.todoguru.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import java.time.LocalDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(
    private val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val tasks = database.getAllTask()

    private var _addTaskEvent = MutableLiveData(false)
    val addTaskEvent: LiveData<Boolean>
        get() = _addTaskEvent

    private var _showTaskFragment = MutableLiveData<Task?>()
    val showTaskFragment: LiveData<Task?>
    get() = _showTaskFragment

    private var task: Task? = null

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun triggerTaskEvent() {
        _addTaskEvent.value = true
    }

    fun insertTask(
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: String
    ) {
        uiScope.launch {
            insert(Task(title, description, dueDate, estimated, reminder))
        }
    }

    fun clickTask(t: Task) {

    }

    private suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            database.insert(task)
        }
    }

    fun showTaskFragment(t: Task){
        task = t
        _showTaskFragment.value = t
    }
}
