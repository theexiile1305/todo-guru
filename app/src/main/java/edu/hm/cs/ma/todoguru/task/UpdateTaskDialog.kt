package edu.hm.cs.ma.todoguru.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Task
import java.time.LocalDate
import java.time.LocalDateTime
import kotlinx.android.synthetic.main.update_task_dialog.topAppBar

class UpdateTaskDialog(
    private val task: Task,
    private val listener: Listener
) : AbstractTaskDialog() {

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

    private fun update() {
        if (validateUserInput()) {
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
}
