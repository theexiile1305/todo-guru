package edu.hm.cs.ma.todoguru.task

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import edu.hm.cs.ma.todoguru.database.Task

class DeleteDialog(private val viewModel: TaskViewModel, private val selectedTasks: ArrayList<Task>) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(this.requireActivity())
        builder.setTitle("Are you sure that you want to delete the selected tasks?")
            .setPositiveButton(
                "Confirm"
            ) { _, _ -> viewModel.deleteTasks(selectedTasks) }
            .setNegativeButton(
                "Cancel"
            ) { _, _ -> }
        return builder.create()
    }
}
