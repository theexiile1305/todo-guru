package edu.hm.cs.ma.todoguru.productivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import timber.log.Timber

class ProductivityViewModel(
    val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: TaskDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductivityViewModel::class.java)) {
                Timber.d("Gather the data from the database of completed and uncompleted tasks")
                return ProductivityViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    val doneTasks = database.getDoneTasks()
    val todoTasks = database.getAllTask()
}
