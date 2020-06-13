package edu.hm.cs.ma.todoguru.task.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Task
import kotlinx.android.synthetic.main.update_task_dialog.topAppBar
import java.time.LocalDateTime

class UpdateTaskDialog : AbstractTaskDialog() {

    private val args: UpdateTaskDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.update_task_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)

        val task = args.task
        setDefaultValues(task)

        determineDueDate()
        determineReminderDate()
        determineReminderTime()

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            update(task)
        }

        topAppBar.setNavigationOnClickListener { dismiss() }
    }

    private fun setDefaultValues(task: Task) {
        title.setText(task.title)
        description.setText(task.description)
        estimated.setText(task.estimated.toString())
        setDueDateText(task.dueDate)
        dueDate = task.dueDate
        setReminderDateText(task.reminder.toLocalDate())
        reminderDate = task.reminder.toLocalDate()
        setReminderTimeText(task.reminder.toLocalTime())
        reminderTime = task.reminder.toLocalTime()
    }

    private fun update(task: Task) {
        if (validateUserInput()) {
            viewModel.updateTask(
                task.id,
                title.text.toString(),
                description.text.toString(),
                dueDate,
                Integer.parseInt(estimated.text.toString()),
                LocalDateTime.of(reminderDate, reminderTime)
            )
            Toast.makeText(mContext, "Task is updated", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}
