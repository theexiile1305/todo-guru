package edu.hm.cs.ma.todoguru.task

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.SubTask
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
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

    private var id by Delegates.notNull<Long>()
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var estimated = MutableLiveData<Int>()
    var dueDate = MutableLiveData(LocalDate.now())
    var reminderDate = MutableLiveData<LocalDate>()
    var reminderTime = MutableLiveData<LocalTime>()
    var reminder = Transformations.switchMap(reminderDate) {
        if (it != null && reminderTime.value != null)
            MutableLiveData(LocalDateTime.of(reminderDate.value, reminderTime.value))
        else
            Toast.makeText(application, "Reminder time is set to current time. To change, update task.", Toast.LENGTH_LONG).show()
        MutableLiveData(LocalDateTime.of(reminderDate.value, LocalTime.now()))
    }
    var category = MutableLiveData<String?>()
    var subTask = MutableLiveData<List<SubTask>>(emptyList())
    var repeat = MutableLiveData<Period?>()
    var priority = MutableLiveData<Boolean>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun setDefaultUpdateValue(task: Task) {
        task.apply {
            this@TaskViewModel.id = id
            this@TaskViewModel.title.value = title
            this@TaskViewModel.description.value = description
            this@TaskViewModel.dueDate.value = dueDate
            this@TaskViewModel.estimated.value = estimated
            this@TaskViewModel.reminderDate.value = reminder.toLocalDate()
            this@TaskViewModel.reminderTime.value = reminder.toLocalTime()
            this@TaskViewModel.category.value = category
            this@TaskViewModel.subTask.value = subTask
            this@TaskViewModel.repeat.value = repeat
            this@TaskViewModel.priority.value = priority
        }
    }

    fun insertTask() {
        uiScope.launch {
            try {
                insert(
                    Task(
                        title.value!!,
                        description.value!!,
                        dueDate.value!!,
                        estimated.value!!,
                        reminder.value!!,
                        category.value,
                        subTask.value!!,
                        repeat.value,
                        priority.value!!
                    )
                )
            } catch (e: NullPointerException) {
                Log.i("Error", "Insert Task in Database Error:" + e)
            }
        }
    }

    fun updateTask() {
        uiScope.launch {
            try {
                update(
                    Task(
                        id,
                        title.value!!,
                        description.value!!,
                        dueDate.value!!,
                        estimated.value!!,
                        reminder.value!!,
                        category.value,
                        subTask.value!!,
                        repeat.value,
                        priority.value!!
                    )
                )
            } catch (e: NullPointerException) {
                Log.i("Error", "Update Task in Database Error: " + e)
            }
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
