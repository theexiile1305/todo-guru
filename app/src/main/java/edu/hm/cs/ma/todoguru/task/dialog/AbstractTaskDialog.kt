package edu.hm.cs.ma.todoguru.task.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

abstract class AbstractTaskDialog : DialogFragment() {

    protected lateinit var mContext: Context
    protected lateinit var title: TextInputEditText
    protected lateinit var description: TextInputEditText
    protected lateinit var estimated: TextInputEditText
    protected lateinit var viewModel: TaskViewModel
    protected lateinit var dueDate: LocalDate
    protected lateinit var reminderDate: LocalDate
    protected lateinit var reminderTime: LocalTime

    private lateinit var dueDateText: TextInputEditText
    private lateinit var reminderDateText: TextInputEditText
    private lateinit var reminderTimeText: TextInputEditText

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window?.setLayout(width, height)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = requireActivity().run {
            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory =
                TaskViewModel.Factory(
                    dataSource,
                    application
                )
            ViewModelProvider(this, viewModelFactory)
                .get(TaskViewModel::class.java)
        }
    }

    protected fun setupView(view: View) {
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        dueDateText = view.findViewById(R.id.dueDate)
        dueDateText.isFocusable = false
        estimated = view.findViewById(R.id.estimated)
        reminderDateText = view.findViewById(R.id.reminder_date)
        reminderDateText.isFocusable = false
        reminderTimeText = view.findViewById(R.id.reminder_time)
        reminderTimeText.isFocusable = false
    }

    protected fun determineReminderDate() {
        reminderDateText.setOnClickListener {
            // DatePickerDialog expects the old Calender class, we are currently using the new
            // java.time.LocalDate which needs a transformation on the month field:
            // e. g. months of Calender class are from 0-11 and in LocalDate from 1-12
            DatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    reminderDate = LocalDate.of(year, month + 1, dayOfMonth)
                    setReminderDateText(reminderDate)
                },
                reminderDate.year,
                reminderDate.monthValue - 1,
                reminderDate.dayOfMonth
            ).show()
        }
    }

    protected fun determineReminderTime() {
        reminderTimeText.setOnClickListener {
            TimePickerDialog(
                mContext,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    reminderTime = LocalTime.of(hourOfDay, minute)
                    setReminderTimeText(reminderTime)
                },
                reminderTime.hour,
                reminderTime.minute,
                false
            ).show()
        }
    }

    protected fun determineDueDate() {
        dueDateText.setOnClickListener {
            // DatePickerDialog expects the old Calender class, we are currently using the new
            // java.time.LocalDate which needs a transformation on the month field:
            // e. g. months of Calender class are from 0-11 and in LocalDate from 1-12
            DatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    dueDate = LocalDate.of(year, month + 1, dayOfMonth)
                    setDueDateText(dueDate)
                },
                dueDate.year,
                dueDate.monthValue - 1,
                dueDate.dayOfMonth
            ).show()
        }
    }

    protected fun setReminderDateText(reminderDate: LocalDate) {
        val formatDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        reminderDateText.setText(reminderDate.format(formatDate))
    }

    protected fun setReminderTimeText(reminderTime: LocalTime) {
        val formatTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        reminderTimeText.setText(reminderTime.format(formatTime))
    }

    protected fun setDueDateText(dueDate: LocalDate) {
        val formatDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        dueDateText.setText(dueDate.format(formatDate))
    }

    protected fun validateUserInput(): Boolean {
        val validation = HashSet<Boolean>()

        validation.add(validate(title, "The title is required"))
        validation.add(validate(description, "The description is required"))
        validation.add(validate(estimated, "The estimation is required"))
        validation.add(validate(dueDateText, "The due date is required"))
        validation.add(validate(reminderDateText, "The reminder date is required"))
        validation.add(validate(reminderTimeText, "The reminder time is required"))
        return validation.remove(true) && validation.isEmpty() && checkDueDateAfterReminder()
    }

    private fun checkDueDateAfterReminder(): Boolean {
        val isValid = dueDate.isAfter(reminderDate)
        if (!isValid)
            Toast.makeText(mContext, "Due date has to be after reminder", Toast.LENGTH_SHORT)
                .show()
        return isValid
    }

    private fun validate(field: TextInputEditText, error: String): Boolean {
        if (field.text.toString().isEmpty()) {
            field.error = error
            return false
        }
        return true
    }
}
