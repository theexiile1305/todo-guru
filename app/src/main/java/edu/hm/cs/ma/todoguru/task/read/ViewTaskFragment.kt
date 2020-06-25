package edu.hm.cs.ma.todoguru.task.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.databinding.ViewTaskFragmentBindingImpl
import edu.hm.cs.ma.todoguru.task.timeTracker.TimeTrackerViewModel
import kotlinx.android.synthetic.main.view_task_fragment.*
import kotlinx.android.synthetic.main.view_task_fragment.description_task
import kotlinx.android.synthetic.main.view_task_fragment.dueDate_task
import kotlinx.android.synthetic.main.view_task_fragment.title_task

class ViewTaskFragment : Fragment() {
    private val args: ViewTaskFragmentArgs by navArgs()
    private lateinit var binding: ViewTaskFragmentBindingImpl
    private lateinit var viewModel: TimeTrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.view_task_fragment, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = args.task

        val allSubDesc = task.subTask.map { it.description }
        val res = allSubDesc.joinToString("\n")

        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).timeTrackerDatabaseDao
            val viewModelFactory = TimeTrackerViewModel.Factory(
                task.id, dataSource, application
            )
            ViewModelProvider(this@ViewTaskFragment, viewModelFactory)
                .get(TimeTrackerViewModel::class.java)
        }

        title_task.text = task.title
        description_task.text = task.description
        dueDate_task.text = task.dueDate.toString()
        reminder_date.text = task.reminder.toString()
        category.text = task.category
        subtask_title.text = res

        binding.timeTrackerViewModel = viewModel
        binding.lifecycleOwner = this
    }
}
