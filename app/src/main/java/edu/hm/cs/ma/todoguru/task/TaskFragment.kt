package edu.hm.cs.ma.todoguru.task

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.hm.cs.ma.todoguru.database.TaskDatabase
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
            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        }
    }

    protected abstract fun updateValues()
    protected abstract fun getExtraValidation(): HashSet<Boolean>
    protected abstract fun openSetReminderDialog()

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

    protected fun validate(field: TextInputEditText, error: String): Boolean {
        if (field.text.toString().isEmpty()) {
            field.error = error
            return false
        }
        return true
    }

    protected fun validateUserInput(): Boolean {
        val extraValidation = getExtraValidation().apply { remove(true) }
        return extraValidation.isEmpty() && checkDueDateAfterReminder()
    }

    private fun checkDueDateAfterReminder(): Boolean {
        val isValid = viewModel.let {
            val dueDate = it.dueDate.value
            val reminderDate = it.reminderDate.value
            if (dueDate == null || reminderDate == null) false
            else dueDate.isAfter(reminderDate)
        }
        if (!isValid)
            Toast.makeText(mContext, "Due date has to be after reminder", Toast.LENGTH_SHORT)
                .show()
        return isValid
    }
}
