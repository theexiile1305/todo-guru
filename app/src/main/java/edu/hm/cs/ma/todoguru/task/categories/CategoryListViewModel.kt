package edu.hm.cs.ma.todoguru.task.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.Category
import edu.hm.cs.ma.todoguru.database.CategoryDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryListViewModel(
    private val database: CategoryDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: CategoryDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryListViewModel::class.java)) {
                return CategoryListViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    val categories = database.getAllCategories()

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun insertCategory(description: String) {
        uiScope.launch {
            insert(Category(description))
        }
    }

    fun deleteCategory(category: Category) {
        uiScope.launch {
            delete(category)
        }
    }

    private suspend fun insert(category: Category) {
        withContext(Dispatchers.IO) {
            database.insert(category)
        }
    }

    private suspend fun delete(category: Category) {
        withContext(Dispatchers.IO) {
            database.delete(category)
        }
    }
}
