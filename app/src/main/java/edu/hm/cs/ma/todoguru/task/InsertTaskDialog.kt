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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.android.synthetic.main.insert_task_dialog.topAppBar

class InsertTaskDialog(
    private val listener: Listener
) : DialogFragment() {

    companion object {
        const val TAG = "insert_task_dialog"
    }

    interface Listener {
        fun onInsertTask(
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

    private var dueDate = LocalDate.now()
    private var reminderDate = LocalDate.now()
    private var reminderTime = LocalTime.now()

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
        return inflater.inflate(R.layout.insert_task_dialog, container, false)
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

        determineDueDate()
        determineReminderDate()
        determineReminderTime()

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            create()
        }

        topAppBar.setNavigationOnClickListener { this.dismiss() }
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
                    val formatDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    reminderDateText.setText(reminderDate.format(formatDate))
                },
                reminderDate.year,
                reminderDate.monthValue - 1,
                reminderDate.dayOfMonth
            ).show()
        }
    }

    private fun determineReminderTime() {
        reminderTimeText.setOnClickListener {
            TimePickerDialog(
                mContext,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    reminderTime = LocalTime.of(hourOfDay, minute)
                    val formatTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
                    reminderTimeText.setText(reminderTime.format(formatTime))
                },
                reminderTime.hour,
                reminderTime.minute,
                false
            ).show()
        }
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
                    val formatDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    dueDateText.setText(dueDate.format(formatDate))
                },
                dueDate.year,
                dueDate.monthValue - 1,
                dueDate.dayOfMonth
            ).show()
        }
    }

    private fun create() {
        val validation = HashSet<Boolean>()

        validation.add(validate(title, "The title is required"))
        validation.add(validate(description, "The description is required"))
        validation.add(validate(estimated, "The estimation is required"))
        validation.add(validate(dueDateText, "The due date is required"))
        validation.add(validate(reminderDateText, "The reminder date is required"))
        validation.add(validate(reminderTimeText, "The reminder time is required"))

        if (validation.remove(true) && validation.isEmpty()) {
            listener.onInsertTask(
                title.text.toString(),
                description.text.toString(),
                dueDate,
                Integer.parseInt(estimated.text.toString()),
                LocalDateTime.of(reminderDate, reminderTime)
            )
            Toast.makeText(mContext, "Task is created", Toast.LENGTH_SHORT).show()
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
