package edu.hm.cs.ma.todoguru.task.insert

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.task.categories.CategoryListViewModel

class InsertCategoryDialog : DialogFragment() {

    private lateinit var viewModel: CategoryListViewModel

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
                .setView(it.layoutInflater.inflate(R.layout.insert_category_dialog, null))
                .setTitle("Add a new category")
                .setPositiveButton("Confirm") { _, _ ->
                    val description =
                        requireDialog().findViewById<EditText>(R.id.categoryDescription)
                    if (validate(description))
                        viewModel.insertCategory(description.text.toString())
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
        }
    }

    private fun validate(field: EditText): Boolean {
        if (field.text.toString().isEmpty()) {
            field.error = "The category cannot be empty!"
            return false
        }
        return true
    }
}
