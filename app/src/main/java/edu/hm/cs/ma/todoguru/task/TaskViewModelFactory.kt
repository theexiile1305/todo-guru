package edu.hm.cs.ma.todoguru.task

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao

class TaskViewModelFactory(
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
