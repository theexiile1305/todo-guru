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
import kotlinx.android.synthetic.main.insert_task_dialog.topAppBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
    private lateinit var reminderText: TextInputEditText

    private var dueDate = LocalDate.now()
    private var reminder = LocalDateTime.now()

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
        reminderText = view.findViewById(R.id.reminder)
        reminderText.isFocusable = false

        determineDueDate()
        determineReminder()

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            create()
        }

        topAppBar.setNavigationOnClickListener { this.dismiss() }
    }

    private fun determineReminder() {
        val localTime = LocalTime.now()
        reminderText.setOnClickListener {
            TimePickerDialog(
                mContext,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    reminder = LocalDateTime.of(dueDate, LocalTime.of(hourOfDay, minute))
                    val format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    reminderText.setText(reminder.format(format))
                },
                localTime.hour,
                localTime.minute,
                false
            ).show()
        }
    }

    private fun determineDueDate() {
        val localDate = LocalDate.now()
        dueDateText.setOnClickListener {
            DatePickerDialog(
                mContext,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    dueDate = LocalDate.of(year, month, dayOfMonth)
                    val formatDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    dueDateText.setText(dueDate.format(formatDate))
                    val updatedReminder = LocalDateTime.of(dueDate, reminder.toLocalTime())
                    val formatDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    reminderText.setText(updatedReminder.format(formatDateTime))
                },
                localDate.year,
                localDate.monthValue,
                localDate.dayOfMonth
            ).show()
        }
    }

    private fun create() {
        val validation = HashSet<Boolean>()

        validation.add(validate(title, "The title is required"))
        validation.add(validate(description, "The description is required"))
        validation.add(validate(estimated, "The estimation is required"))
        validation.add(validate(dueDateText, "The due date is required"))
        validation.add(validate(reminderText, "The reminder is required"))

        if (validation.remove(true) && validation.isEmpty()) {
            listener.onInsertTask(
                title.text.toString(),
                description.text.toString(),
                dueDate,
                Integer.parseInt(estimated.text.toString()),
                reminder
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
