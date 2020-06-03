package edu.hm.cs.ma.todoguru.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.android.synthetic.main.update_task_dialog.topAppBar

class UpdateTaskDialog(
    private val task: Task,
    private val listener: Listener
) : DialogFragment() {

    companion object {
        const val TAG = "update_task_dialog"
    }

    interface Listener {
        fun onUpdateTask(
            id: Long,
            title: String,
            description: String,
            dueDate: LocalDate,
            estimated: Int,
            reminder: LocalDateTime
        )
    }

    private lateinit var mContext: Context

    private lateinit var title: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var dueDateText: TextInputEditText
    private lateinit var estimated: TextInputEditText
    private lateinit var reminderDateText: TextInputEditText
    private lateinit var reminderTimeText: TextInputEditText

    private var dueDate = task.dueDate
    private var reminderDate = task.reminder.toLocalDate()
    private var reminderTime = task.reminder.toLocalTime()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.update_task_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window?.setLayout(width, height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        dueDateText = view.findViewById(R.id.dueDate)
        dueDateText.isFocusable = false
        estimated = view.findViewById(R.id.estimated)
        reminderDateText = view.findViewById(R.id.reminder_date)
        reminderDateText.isFocusable = false
        reminderTimeText = view.findViewById(R.id.reminder_time)
        reminderTimeText.isFocusable = false

        setDefaultValues()

        determineDueDate()
        determineReminderDate()
        determineReminderTime()

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            update()
        }

        topAppBar.setNavigationOnClickListener { this.dismiss() }
    }

    private fun setDefaultValues() {
        title.setText(task.title)
        description.setText(task.description)
        estimated.setText(task.estimated.toString())
        setReminderDateText()
        setReminderTimeText()
        setDueDateText()
    }

    private fun determineReminderDate() {
        reminderDateText.setOnClickListener {
            // DatePickerDialog expects the old Calender class, we are currently using the new
            // java.time.LocalDate which needs a transformation on the month field:
            // e. g. months of Calender class are from 0-11 and in LocalDate from 1-12
            DatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    reminderDate = LocalDate.of(year, month + 1, dayOfMonth)
                    setReminderDateText()
                },
                reminderDate.year,
                reminderDate.monthValue - 1,
                reminderDate.dayOfMonth
            ).show()
        }
    }

    private fun setReminderDateText() {
        val formatDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        reminderDateText.setText(reminderDate.format(formatDate))
    }

    private fun determineReminderTime() {
        reminderTimeText.setOnClickListener {
            TimePickerDialog(
                mContext,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    reminderTime = LocalTime.of(hourOfDay, minute)
                    setReminderTimeText()
                },
                reminderTime.hour,
                reminderTime.minute,
                false
            ).show()
        }
    }

    private fun setReminderTimeText() {
        val formatTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        reminderTimeText.setText(reminderTime.format(formatTime))
    }

    private fun determineDueDate() {
        dueDateText.setOnClickListener {
            // DatePickerDialog expects the old Calender class, we are currently using the new
            // java.time.LocalDate which needs a transformation on the month field:
            // e. g. months of Calender class are from 0-11 and in LocalDate from 1-12
            DatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    dueDate = LocalDate.of(year, month + 1, dayOfMonth)
                    setDueDateText()
                },
                dueDate.year,
                dueDate.monthValue - 1,
                dueDate.dayOfMonth
            ).show()
        }
    }

    private fun setDueDateText() {
        val formatDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        dueDateText.setText(dueDate.format(formatDate))
    }

    private fun update() {
        val validation = HashSet<Boolean>()

        validation.add(validate(title, "The title is required"))
        validation.add(validate(description, "The description is required"))
        validation.add(validate(estimated, "The estimation is required"))
        validation.add(validate(dueDateText, "The due date is required"))
        validation.add(validate(reminderDateText, "The reminder date is required"))
        validation.add(validate(reminderTimeText, "The reminder time is required"))

        if (validation.remove(true) && validation.isEmpty()) {
            listener.onUpdateTask(
                task.id,
                title.text.toString(),
                description.text.toString(),
                dueDate,
                Integer.parseInt(estimated.text.toString()),
                LocalDateTime.of(reminderDate, reminderTime)
            )
            Toast.makeText(mContext, "Task is updated", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }

    private fun validate(field: TextInputEditText, error: String): Boolean {
        if (field.text.toString().isEmpty()) {
            field.error = error
            return false
        }
        return true
    }
}
