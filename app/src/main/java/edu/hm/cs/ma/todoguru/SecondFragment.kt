package edu.hm.cs.ma.todoguru

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var databaseJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + databaseJob)

    private lateinit var tasksFromDB: TaskDatabaseDao
    private lateinit var mContext: Context
    private lateinit var mDateSetListener: OnDateSetListener

    private lateinit var title: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var dueDate: TextInputEditText
    private lateinit var estimated: TextInputEditText
    private lateinit var dat: LocalDate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tasksFromDB = TaskDatabase.getInstance(mContext).taskDatabaseDao
        (activity as MainActivity).supportActionBar?.title = getString(R.string.addTask)

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
        description = view.findViewById<TextInputEditText>(R.id.description)
        dueDate = view.findViewById<TextInputEditText>(R.id.dueDate)
        estimated = view.findViewById<TextInputEditText>(R.id.estimated)

        dueDate.isFocusable = false

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            create()
        }

        dueDate.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val year: Int = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day: Int = cal.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(
                mContext,
                android.R.style.Theme_Material_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day

            )
            dialog.show()
        }

        mDateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
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

        if (isValid) {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    tasksFromDB.insert(
                        Task(
                            0L,
                            title.text.toString(),
                            description.text.toString(),
                            date(),
                            Integer.parseInt(estimated.text.toString()),
                            ""
                        )
                    )
                }
            }
            Toast.makeText(mContext, "Task is created", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun date(): LocalDate {
        val formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy")

        return LocalDate.parse(dueDate.text.toString(), formatter)
        }
}
