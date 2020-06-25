package edu.hm.cs.ma.todoguru.productivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import edu.hm.cs.ma.todoguru.task.list.TaskListViewModel

class ProductivityViewModel (
    private val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: TaskDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductivityViewModel::class.java)) {
                return ProductivityViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    val doneTasks = database.getDoneTasks()
    val todoTasks = database.getAllTasks()
}