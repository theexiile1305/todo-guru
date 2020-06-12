package edu.hm.cs.ma.todoguru.task

import edu.hm.cs.ma.todoguru.database.Task

class TaskListener(private val viewModel: TaskViewModel) {
    fun onClick(t: Task) = viewModel.clickTask(t)
}
