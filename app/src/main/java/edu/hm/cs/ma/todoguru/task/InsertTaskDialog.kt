package edu.hm.cs.ma.todoguru.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import edu.hm.cs.ma.todoguru.R
import java.time.LocalDate

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
            reminder: String
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

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window?.setLayout(width, height)
        }
    }
}