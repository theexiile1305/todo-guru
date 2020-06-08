package edu.hm.cs.ma.todoguru

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
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
import kotlinx.android.synthetic.main.fragment_task_list.topAppBar

class MainActivity :
    AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, ReminderNotification::class.java))

        setContentView(R.layout.activity_main)

    }

}
