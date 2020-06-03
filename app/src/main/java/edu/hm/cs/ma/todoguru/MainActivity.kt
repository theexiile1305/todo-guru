package edu.hm.cs.ma.todoguru

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.ActivityMainBinding
import edu.hm.cs.ma.todoguru.notification.ReminderNotification
import edu.hm.cs.ma.todoguru.task.DeleteDialog
import edu.hm.cs.ma.todoguru.task.InsertTaskDialog
import edu.hm.cs.ma.todoguru.task.TaskAdapter
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import edu.hm.cs.ma.todoguru.task.TaskViewModelFactory
import edu.hm.cs.ma.todoguru.task.TaskWrapper
import edu.hm.cs.ma.todoguru.task.UpdateTaskDialog
import java.time.LocalDate
import java.time.LocalDateTime
import kotlinx.android.synthetic.main.activity_main.topAppBar

class MainActivity : InsertTaskDialog.Listener, UpdateTaskDialog.Listener, TaskAdapter.Listener,
    AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel

    private val selectedTasks = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = TaskViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = TaskAdapter(this)
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(this, Observer {
            it.let { adapter.submitList(it) }
        })

        viewModel.addTaskEvent.observe(this, Observer {
            if (it)
                InsertTaskDialog(this).show(supportFragmentManager, InsertTaskDialog.TAG)
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

        viewModel.markTaskDoneEvent.observe(this, Observer {
            if (it)
                selectedTasks.clear()
        })

        viewModel.deleteTaskEvent.observe(this, Observer {
            if (it)
                selectedTasks.clear()
        })

        startService(Intent(this, ReminderNotification::class.java))
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
        UpdateTaskDialog(task, this).show(supportFragmentManager, UpdateTaskDialog.TAG)

    private fun openDeleteDialog() {
        val dialog = DeleteDialog(viewModel, selectedTasks)
        dialog.show(supportFragmentManager, DeleteDialog.TAG)
    }
}
