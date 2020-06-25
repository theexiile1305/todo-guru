package edu.hm.cs.ma.todoguru.task.list

import edu.hm.cs.ma.todoguru.database.Task

data class TaskWrapper(
    val task: Task,
    var isSelected: Boolean = false
)
