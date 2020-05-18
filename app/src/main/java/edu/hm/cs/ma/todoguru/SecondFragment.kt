package edu.hm.cs.ma.todoguru

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    // Coroutine stuff
    private var databaseJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + databaseJob)

    private lateinit var tasksFromDB: TaskDatabaseDao
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tasksFromDB = TaskDatabase.getInstance(mContext).taskDatabaseDao
        (activity as MainActivity).supportActionBar?.title = getString(R.string.addTask)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<TextInputEditText>(R.id.title).text
        val description = view.findViewById<EditText>(R.id.description).text
        val dueDate = view.findViewById<EditText>(R.id.dueDate).text
        val estimated = view.findViewById<EditText>(R.id.estimated).text

        uiScope.launch {
            withContext(Dispatchers.IO) {
                tasksFromDB.insert(
                    Task(
                        0L,
                        title.toString(),
                        description.toString(),
                        dueDate.toString(),
                        estimated.toString(),
                        ""
                    )
                )
            }
        }

        view.findViewById<Button>(R.id.button_create).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}