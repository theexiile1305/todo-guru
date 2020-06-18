package edu.hm.cs.ma.todoguru.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.TimeTrackerDatabase
import edu.hm.cs.ma.todoguru.databinding.ViewTaskFragmentBinding
import edu.hm.cs.ma.todoguru.timeTracker.TimeTrackerViewModel
import edu.hm.cs.ma.todoguru.timeTracker.TimeTrackerViewModelFactory
import kotlinx.android.synthetic.main.view_task_fragment.description_task
import kotlinx.android.synthetic.main.view_task_fragment.dueDate_task
import kotlinx.android.synthetic.main.view_task_fragment.title_task

class ViewTaskFragment : Fragment() {
    private val args: ViewTaskFragmentArgs by navArgs()
    private lateinit var binding: ViewTaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // super.onCreateView(inflater, container, savedInstanceState)
       // return inflater.inflate(R.layout.view_task_fragment, container, false)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.view_task_fragment, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = args.task

        title_task.text = task.title
        description_task.text = task.description
        dueDate_task.text = task.dueDate.toString()

        val application = requireNotNull(this.activity).application

        val dataSource = TimeTrackerDatabase.getInstance(application).timeTrackerDatabaseDao

        val viewModelFactory = TimeTrackerViewModelFactory(dataSource, application)

        val timeTrackerViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(TimeTrackerViewModel::class.java)

        binding.timeTrackerViewModel = timeTrackerViewModel

        binding.setLifecycleOwner(this)
    }
}
