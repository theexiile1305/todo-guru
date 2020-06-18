package edu.hm.cs.ma.todoguru.task.delete

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.task.categories.CategoryListViewModel
import edu.hm.cs.ma.todoguru.task.list.TaskListViewModel

class DeleteCategoryDialog : DialogFragment() {

    private lateinit var viewModel: CategoryListViewModel

    private val args: DeleteCategoryDialogArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).categoryDatabaseDao
            val viewModelFactory = CategoryListViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(CategoryListViewModel::class.java)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Are you sure that you want to delete the category?")
                .setPositiveButton("Confirm") { _, _ -> viewModel.deleteCategory(args.category) }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
        }
    }
}
