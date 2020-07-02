package edu.hm.cs.ma.todoguru.task.completedTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.databinding.CompletedTaskListFragmentBinding

class CompletedTaskListFragment : CompletedTaskAdapter.Listener, Fragment() {

    private lateinit var binding: CompletedTaskListFragmentBinding
    private lateinit var viewModel: CompletedTaskListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.completed_task_list_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = CompletedTaskListViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(CompletedTaskListViewModel::class.java)
        }

        val adapter = CompletedTaskAdapter(this)
        binding.apply {
            viewModel = this@CompletedTaskListFragment.viewModel
            lifecycleOwner = this@CompletedTaskListFragment
            completedTaskList.adapter = adapter
        }
        viewModel.apply {
            completedTasks.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.submitList(it)
                }
            )
        }
    }

    override fun onClick(task: Task) {
        findNavController().navigate(CompletedTaskListFragmentDirections.actionCompletedTaskListFragmentToViewTaskFragment(task))
    }
}
