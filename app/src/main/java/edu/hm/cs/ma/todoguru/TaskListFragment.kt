package edu.hm.cs.ma.todoguru

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.FragmentTaskListBinding
import edu.hm.cs.ma.todoguru.notification.ReminderNotification
import edu.hm.cs.ma.todoguru.task.*
import kotlinx.android.synthetic.main.fragment_task_list.*
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment(), InsertTaskDialog.Listener, UpdateTaskDialog.Listener,
    TaskAdapter.Listener {

    private lateinit var viewModel: TaskViewModel
    private val selectedTasks = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTaskListBinding = DataBindingUtil.inflate<FragmentTaskListBinding>(
            inflater,
            R.layout.fragment_task_list,
            container,
            false
        )
        viewModel = requireActivity().run {
            val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
            val viewModelFactory = TaskViewModelFactory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = TaskAdapter(this)
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(requireActivity(), Observer {
            it.let { adapter.submitList(it) }
        })

        viewModel.addTaskEvent.observe(requireActivity(), Observer {
            if (it)
                InsertTaskDialog(this).show(requireActivity().supportFragmentManager, InsertTaskDialog.TAG)
        })

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.mark_tasks_as_done -> {
                    viewModel.markTasksAsDone(selectedTasks)
                    true
                }
                R.id.delete_tasks -> {
                    if (selectedTasks.isNotEmpty()) {
                        openDeleteDialog()
                    }
                    true
                }
                else -> false
            }
        }

        viewModel.markTaskDoneEvent.observe(requireActivity(), Observer {
            if (it)
                selectedTasks.clear()
        })

        viewModel.deleteTaskEvent.observe(requireActivity(), Observer {
            if (it)
                selectedTasks.clear()
        })


        return binding.root
    }




    override fun onTaskClick(task: Task) {
        Log.i("tag", task.toString())
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        // fragmentTransaction.replace(R.id.mainContainer, ViewTaskFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onInsertTask(
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: LocalDateTime
    ) {
        viewModel.insertTask(title, description, dueDate, estimated, reminder)
    }

    override fun onUpdateTask(
        id: Long,
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: LocalDateTime
    ) {
        viewModel.updateTask(id, title, description, dueDate, estimated, reminder)
    }

    override fun onCheckBoxClick(wrapper: TaskWrapper) {
        val task = wrapper.task
        if (selectedTasks.contains(task))
            selectedTasks.remove(task)
        else
            selectedTasks.add(task)
        wrapper.isSelected = true
    }

    override fun onUpdateClick(task: Task) {
        UpdateTaskDialog(task, this).show(requireActivity().supportFragmentManager, UpdateTaskDialog.TAG)
    }

    private fun openDeleteDialog() {
        val dialog = DeleteDialog(viewModel, selectedTasks)
        dialog.show(requireActivity().supportFragmentManager, DeleteDialog.TAG)
    }

}