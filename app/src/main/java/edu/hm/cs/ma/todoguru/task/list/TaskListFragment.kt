package edu.hm.cs.ma.todoguru.task.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.TaskListFragmentBinding
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import kotlinx.android.synthetic.main.task_list_fragment.topAppBar

class TaskListFragment : TaskAdapter.Listener, Fragment() {

    private lateinit var binding: TaskListFragmentBinding
    private lateinit var viewModel: TaskViewModel

    private val selectedTasks = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.task_list_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(topAppBar)

        viewModel = requireActivity().run {
            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskViewModel.Factory(dataSource, application)
            ViewModelProvider(this@TaskListFragment, viewModelFactory)
                .get(TaskViewModel::class.java)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = TaskAdapter(this)
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )
        viewModel.addTaskEvent.observe(
            viewLifecycleOwner,
            Observer {
                if (it) openInsertDialog()
            }
        )
        viewModel.markTaskDoneEvent.observe(
            viewLifecycleOwner,
            Observer {
                if (it) selectedTasks.clear()
            }
        )
        viewModel.deleteTaskEvent.observe(
            viewLifecycleOwner,
            Observer {
                if (it) selectedTasks.clear()
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mark_tasks_as_done -> markTaskAsDone()
            R.id.delete_tasks -> deleteTasks()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCheckBoxClick(wrapper: TaskWrapper) {
        val task = wrapper.task
        if (selectedTasks.contains(task)) selectedTasks.remove(task)
        else selectedTasks.add(task)
        wrapper.isSelected = true
    }

    private fun markTaskAsDone() = viewModel.markTasksAsDone(selectedTasks)

    private fun deleteTasks() {
        if (selectedTasks.isNotEmpty()) {
            openDeleteDialog()
        }
    }

    private fun openInsertDialog() {
        findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToInsertTaskDialog())
    }

    override fun onUpdateClick(task: Task) {
        findNavController().navigate(
            TaskListFragmentDirections.actionTaskListFragmentToUpdateTaskDialog(
                task
            )
        )
    }

    private fun openDeleteDialog() {
        findNavController().navigate(
            TaskListFragmentDirections.actionTaskListFragmentToDeleteDialog(
                selectedTasks.toTypedArray()
            )
        )
    }
}
