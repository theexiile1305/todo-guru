package edu.hm.cs.ma.todoguru

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.ViewTaskFragmentBinding
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import edu.hm.cs.ma.todoguru.task.TaskViewModelFactory

class ViewTaskFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

      val binding = DataBindingUtil.inflate<ViewTaskFragmentBinding>(inflater, R.layout.view_task_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = TaskViewModelFactory(dataSource, application)
        val taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)
        binding.taskViewModel = taskViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }
}