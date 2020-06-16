package edu.hm.cs.ma.todoguru.task.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.task.list.TaskListViewModel

class DeleteDialog : DialogFragment() {

    private lateinit var viewModel: TaskListViewModel

    private val args: DeleteDialogArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = requireActivity().run {
            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskListViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskListViewModel::class.java)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val selectedTasks: List<Task> = args.taskList.toList()
        return AlertDialog
            .Builder(requireActivity())
            .setTitle("Are you sure that you want to delete the selected tasks?")
            .setPositiveButton("Confirm") { _, _ -> viewModel.deleteTasks(selectedTasks) }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
    }
}
