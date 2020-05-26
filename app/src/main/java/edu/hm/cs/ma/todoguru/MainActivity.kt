package edu.hm.cs.ma.todoguru

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.ActivityMainBinding
import edu.hm.cs.ma.todoguru.task.InsertTaskDialog
import edu.hm.cs.ma.todoguru.task.TaskAdapter
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import edu.hm.cs.ma.todoguru.task.TaskViewModelFactory
import java.time.LocalDate
import java.time.LocalDateTime

class MainActivity : InsertTaskDialog.Listener, AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = TaskViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = TaskAdapter()
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(this, Observer {
            it.let { adapter.submitList(it) }
        })

        viewModel.addTaskEvent.observe(this, Observer {
            if (it) {
                InsertTaskDialog(this).show(supportFragmentManager, InsertTaskDialog.TAG)
            }
        })
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
}
