package edu.hm.cs.ma.todoguru.task

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.task.categories.CategoryListViewModel
import java.util.stream.Collectors

class SetCategoryDialog : DialogFragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var categoryViewModel: CategoryListViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViewModels()
        val items = categoryViewModel.getSingleChoiceItems()
        var chosen: String = ""
        return requireActivity().let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Select a category")
                .setSingleChoiceItems(items, 1) { _, which ->
                    chosen = items[which]
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .setPositiveButton("Confirm") { _, _ -> taskViewModel.category.value = chosen }
                .create()
        }
    }

    private fun initViewModels() {
        requireActivity().let {
            val dataSource = ToDoGuruDatabase.getInstance(it)
            TaskViewModel.Factory(dataSource.taskDatabaseDao, requireActivity().application).apply {
                taskViewModel = ViewModelProvider(it, this).get(TaskViewModel::class.java)
            }
            CategoryListViewModel.Factory(
                dataSource.categoryDatabaseDao,
                requireActivity().application
            ).apply {
                categoryViewModel =
                    ViewModelProvider(it, this).get(CategoryListViewModel::class.java)
            }
        }
    }

    private fun CategoryListViewModel.getSingleChoiceItems(): Array<String> {
        return (categories.value ?: emptyList())
            .stream()
            .map { it.description }
            .collect(Collectors.toList())
            .toTypedArray()
    }
}
