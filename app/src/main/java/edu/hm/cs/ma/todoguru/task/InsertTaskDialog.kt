package edu.hm.cs.ma.todoguru.task

import android.app.DatePickerDialog
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
import java.time.format.DateTimeFormatter
import java.util.Calendar

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
    private lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener

    private lateinit var title: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var dueDate: TextInputEditText
    private lateinit var estimated: TextInputEditText

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
        dueDate = view.findViewById(R.id.dueDate)
        estimated = view.findViewById(R.id.estimated)
        dueDate.isFocusable = false

        view.findViewById<Button>(R.id.button_create)
            .setOnClickListener { create() }

        dueDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                mContext,
                android.R.style.Theme_Material_Light_Dialog_MinWidth,
                mDateSetListener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)

            ).show()
        }

        mDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                var currentMonth = month
                var dayString = day.toString()
                var monthString = currentMonth.toString()
                val yearString = year.toString()

                currentMonth += 1

                if (day < 10) {
                    dayString = "0$day"
                }

                if (currentMonth < 10) {
                    monthString = "0$currentMonth"
                }

                val date = "$dayString/$monthString/$yearString"
                dueDate.setText(date)
            }
    }

    private fun create() {
        var isValid = true

        if (title.text.toString().isEmpty()) {
            title.error = "The title is required"
            isValid = false
        }

        if (description.text.toString().isEmpty()) {
            description.error = "The description required"
            isValid = false
        }

        if (estimated.text.toString().isEmpty()) {
            estimated.error = "The estimation required"
            isValid = false
        }

        if (isValid) {
            listener.onInsertTask(
                title.text.toString(),
                description.text.toString(),
                date(),
                Integer.parseInt(estimated.text.toString()),
                LocalDateTime.now()
            )
            Toast.makeText(mContext, "Task is created", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }

    private fun date(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(dueDate.text.toString(), formatter)
    }
}
