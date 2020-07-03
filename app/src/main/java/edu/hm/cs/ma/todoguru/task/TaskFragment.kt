package edu.hm.cs.ma.todoguru.task

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import java.time.LocalDate

abstract class TaskFragment : Fragment() {
    private lateinit var mContext: Context
    protected lateinit var viewModel: TaskViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        }
    }

    protected abstract fun openSetReminderDialog()
    protected abstract fun getTitle(): TextInputEditText
    protected abstract fun getDescription(): TextInputEditText
    protected abstract fun getEstimated(): TextInputEditText
    protected abstract fun getPriority(): Switch

    protected fun openSetDueDateDialog(dueDate: LocalDate) {
        DatePickerDialog(
            mContext,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.dueDate.value = LocalDate.of(year, month + 1, dayOfMonth)
            },
            dueDate.year,
            dueDate.monthValue - 1,
            dueDate.dayOfMonth
        ).show()
    }

    protected fun updateValues() {
        viewModel.title.value = getTitle().text.toString()
        viewModel.description.value = getDescription().text.toString()
        viewModel.estimated.value =
            if (getEstimated().text.toString().isEmpty()) 0
            else getEstimated().text.toString().toInt()
        viewModel.priority.value = getPriority().isChecked
    }

    protected fun validateUserInput(): Boolean {
        val validation = HashSet<Boolean>().apply {
            add(validate(getTitle(), "The title is required"))
            add(validate(getDescription(), "The description is required"))
            add(validate(getEstimated(), "The estimation is required"))
        }.also { it.remove(true) }

        return validation.isEmpty() && checkDueDateAfterReminder()
    }

    private fun validate(field: TextInputEditText, error: String): Boolean {
        if (field.text.toString().isEmpty()) {
            field.error = error
            return false
        }
        return true
    }

    private fun checkDueDateAfterReminder(): Boolean {
        val isValid = viewModel.let {
            val dueDate = it.dueDate.value
            val reminderDate = it.reminderDate.value
            dueDate != null && reminderDate != null && dueDate.plusDays(1).isAfter(reminderDate)
        }
        if (!isValid)
            Toast.makeText(mContext, "Due date has to be after reminder", Toast.LENGTH_SHORT)
                .show()
        return isValid
    }
}
