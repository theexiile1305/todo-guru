package edu.hm.cs.ma.todoguru.task

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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.properties.Delegates

class TaskViewModel(
    private val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: TaskDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                return TaskViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    var dueDate = MutableLiveData(LocalDate.now())
    var reminderDate = MutableLiveData(LocalDate.now())
    var reminderTime = MutableLiveData(LocalTime.now())
    private var id by Delegates.notNull<Long>()

    private var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private var _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private var _estimated = MutableLiveData<Int>()
    val estimated: LiveData<Int>
        get() = _estimated

    private var _createTaskEvent = MutableLiveData<Boolean>()
    val createTaskEvent: LiveData<Boolean>
        get() = _createTaskEvent

    private var _addDueDateEvent = MutableLiveData<Boolean>()
    val addDueDateEvent: LiveData<Boolean>
        get() = _addDueDateEvent

    private var _addReminderEvent = MutableLiveData<Boolean>()
    val addReminderEvent: LiveData<Boolean>
        get() = _addReminderEvent

    private var _addReminderTimeEvent = MutableLiveData<Boolean>()
    val addReminderTimeEvent: LiveData<Boolean>
        get() = _addReminderTimeEvent

    private var _addReminderDateEvent = MutableLiveData<Boolean>()
    val addReminderDateEvent: LiveData<Boolean>
        get() = _addReminderDateEvent

    private var _insertReminderEvent = MutableLiveData<Boolean>()
    val insertReminderEvent: LiveData<Boolean>
        get() = _insertReminderEvent

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun triggerCreateTaskEvent() {
        _createTaskEvent.value = true
    }

    fun triggerAddDueDateEvent() {
        _addDueDateEvent.value = true
    }

    fun triggerAddReminderEvent() {
        _addReminderEvent.value = true
    }

    fun triggerAddReminderDateEvent() {
        _addReminderDateEvent.value = true
    }

    fun triggerAddReminderTimeEvent() {
        _addReminderTimeEvent.value = true
    }

    fun triggerInsertReminderEvent() {
        _insertReminderEvent.value = true
    }

    fun setDefaultUpdateValue(task: Task) {
        task.apply {
            _title.value = title
            _description.value = description
            _estimated.value = estimated
            this@TaskViewModel.id = id
            this@TaskViewModel.dueDate.value = dueDate
            this@TaskViewModel.reminderDate.value = reminder.toLocalDate()
            this@TaskViewModel.reminderTime.value = reminder.toLocalTime()
        }
    }

    fun insertTask(
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: LocalDateTime
    ) {
        uiScope.launch {
            insert(Task(title, description, dueDate, estimated, reminder))
        }
    }

    fun updateTask(
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: LocalDateTime
    ) {
        uiScope.launch {
            update(Task(id, title, description, dueDate, estimated, reminder))
        }
    }

    private suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            database.insert(Task(task))
        }
    }

    private suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }
}
