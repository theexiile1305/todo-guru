package edu.hm.cs.ma.todoguru

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
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

    // Coroutine stuff
    private var databaseJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + databaseJob)

    private lateinit var tasksFromDB: TaskDatabaseDao
    private lateinit var mContext: Context
    private lateinit var mDateSetListener: OnDateSetListener

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
        tasksFromDB = TaskDatabase.getInstance(mContext).taskDatabaseDao
        (activity as MainActivity).supportActionBar?.title = getString(R.string.addTask)
        // Inflate the layout for this fragment
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
            // dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        mDateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var currentMonth = month
                currentMonth += 1
                Log.i(TAG, "onDateSet: dd/mm/yyy: $day/$currentMonth/$year")
                val date = "$day/$currentMonth/$year"
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
                            dueDate.text.toString(),
                            estimated.text.toString(),
                            ""
                        )
                    )
                }
            }
            Toast.makeText(mContext, "Task is created", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}
