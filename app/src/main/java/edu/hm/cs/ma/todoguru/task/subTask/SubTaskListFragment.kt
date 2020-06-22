package edu.hm.cs.ma.todoguru.task.subTask

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
import edu.hm.cs.ma.todoguru.database.SubTask
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.databinding.SubtaskListFragmentBinding
import edu.hm.cs.ma.todoguru.task.TaskViewModel

class SubTaskListFragment : SubTaskAdapter.Listener, Fragment() {

    private lateinit var binding: SubtaskListFragmentBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var subTaskViewModel: SubTaskListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.subtask_list_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().let {
            val dataSource = ToDoGuruDatabase.getInstance(it)
            TaskViewModel.Factory(dataSource.taskDatabaseDao, requireActivity().application).apply {
                taskViewModel = ViewModelProvider(it, this).get(TaskViewModel::class.java)
            }
            SubTaskListViewModel.Factory(
                dataSource.subTaskDatabaseDao,
                requireActivity().application
            ).apply {
                subTaskViewModel =
                    ViewModelProvider(it, this).get(SubTaskListViewModel::class.java)
            }
        }

        val adapter = SubTaskAdapter(this)
        binding.apply {
            viewModel = this@SubTaskListFragment.subTaskViewModel
            lifecycleOwner = this@SubTaskListFragment
            subTaskList.adapter = adapter

            createTaskButton.setOnClickListener { openInsertDialog() }
        }

        subTaskViewModel.apply {
            subTasks.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.submitList(it)
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        taskViewModel.subTask.value = subTaskViewModel.subTasks.value
    }

    private fun openInsertDialog() {
        findNavController().navigate(SubTaskListFragmentDirections.actionSubTaskListFragmentToInsertSubTaskDialog())
    }

    override fun onDeleteClick(SubTask: SubTask) {
        findNavController().navigate(
            SubTaskListFragmentDirections.actionSubTaskListFragmentToDeleteSubTaskDialog(
                SubTask
            )
        )
    }
}
