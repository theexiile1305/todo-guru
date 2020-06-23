package edu.hm.cs.ma.todoguru.task.delete

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.task.subTask.SubTaskListViewModel

class DeleteSubTaskDialog : DialogFragment() {

    private lateinit var viewModel: SubTaskListViewModel

    private val args: DeleteSubTaskDialogArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).subTaskDatabaseDao
            val viewModelFactory = SubTaskListViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(SubTaskListViewModel::class.java)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Are you sure that you want to delete the sub task?")
                .setPositiveButton("Confirm") { _, _ -> viewModel.deleteSubTask(args.subTask) }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
        }
    }
}
