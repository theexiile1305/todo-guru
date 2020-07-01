package edu.hm.cs.ma.todoguru.task.completedTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao

class CompletedTaskListViewModel(
    val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: TaskDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CompletedTaskListViewModel::class.java)) {
                return CompletedTaskListViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    val completedTasks = database.getAllCompletedTask()
}
