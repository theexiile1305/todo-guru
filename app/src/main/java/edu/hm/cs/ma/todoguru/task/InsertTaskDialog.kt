package edu.hm.cs.ma.todoguru.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import edu.hm.cs.ma.todoguru.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlinx.android.synthetic.main.insert_task_dialog.topAppBar

class InsertTaskDialog(
    private val listener: Listener
) : AbstractTaskDialog(LocalDate.now(), LocalDate.now(), LocalTime.now()) {

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

        determineDueDate()
        determineReminderDate()
        determineReminderTime()

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            create()
        }

        topAppBar.setNavigationOnClickListener { this.dismiss() }
    }

    private fun create() {
        if (validateUserInput()) {
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
}
