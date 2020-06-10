package edu.hm.cs.ma.todoguru.task.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import edu.hm.cs.ma.todoguru.R
import kotlinx.android.synthetic.main.insert_task_dialog.topAppBar
import java.time.LocalDateTime

class InsertTaskDialog : AbstractTaskDialog() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.insert_task_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)

        setDefaultValues()

        determineDueDate()
        determineReminderDate()
        determineReminderTime()

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            create()
        }

        topAppBar.setNavigationOnClickListener { dismiss() }
    }

    private fun setDefaultValues() {
        val dateTime = LocalDateTime.now()
        setDueDateText(dateTime.toLocalDate())
        dueDate = dateTime.toLocalDate()
        setReminderDateText(dateTime.toLocalDate())
        reminderDate = dateTime.toLocalDate()
        setReminderTimeText(dateTime.toLocalTime())
        reminderTime = dateTime.toLocalTime()
    }

    private fun create() {
        if (validateUserInput()) {
            viewModel.insertTask(
                title.text.toString(),
                description.text.toString(),
                dueDate,
                Integer.parseInt(estimated.text.toString()),
                LocalDateTime.of(reminderDate, reminderTime)
            )
            Toast.makeText(mContext, "Task is created", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}
