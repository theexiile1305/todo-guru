package edu.hm.cs.ma.todoguru.task.delete

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.task.list.TaskListViewModel

class DeleteTaskDialog : DialogFragment() {

    private lateinit var viewModel: TaskListViewModel

    private val args: DeleteTaskDialogArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskListViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskListViewModel::class.java)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val selectedTasks: List<Task> = args.taskList.toList()
        return requireActivity().let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Are you sure that you want to delete the selected tasks?")
                .setPositiveButton("Confirm") { _, _ -> viewModel.deleteTasks(selectedTasks) }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
        }
    }
}
