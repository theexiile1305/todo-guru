package edu.hm.cs.ma.todoguru.task

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import timber.log.Timber
import java.time.Period

class SetRepeatDialog : DialogFragment() {

    companion object {
        const val EVERY_DAY = "every day"
        const val EVERY_WEEK = "every week"
        const val EVERY_MONTH = "every month"
    }

    private lateinit var viewModel: TaskViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Timber.d("Allows the user to put a specific task on repeat")
        initViewModels()
        val items = getSingleChoiceItems()
        var chosen: String? = null
        return requireActivity().let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Select task on repeat")
                .setSingleChoiceItems(items, 1) { _, which -> chosen = items[which] }
                .setNegativeButton("Cancel") { _, _ -> }
                .setPositiveButton("Confirm") { _, _ ->
                    viewModel.repeat.value = when (chosen) {
                        EVERY_DAY -> Period.ofDays(1)
                        EVERY_WEEK -> Period.ofDays(7)
                        EVERY_MONTH -> Period.ofMonths(1)
                        else -> null
                    }
                }
                .create()
        }
    }

    private fun initViewModels() {
        requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this)
            val viewModelFactory =
                TaskViewModel.Factory(dataSource.taskDatabaseDao, this.application)
            viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        }
    }

    private fun getSingleChoiceItems() = arrayOf(EVERY_DAY, EVERY_WEEK, EVERY_MONTH)
}
