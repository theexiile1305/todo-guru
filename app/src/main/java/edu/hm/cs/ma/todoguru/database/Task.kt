package edu.hm.cs.ma.todoguru.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "due Date")
    var dueDate: LocalDate,
    @ColumnInfo(name = "estimated Time")
    var estimated: Int,
    @ColumnInfo(name = "reminder")
    var reminder: LocalDateTime,
    @ColumnInfo(name = "done")
    var done: Boolean
) {

    constructor(
        task: Task
    ) : this(
        task.title,
        task.description,
        task.dueDate,
        task.estimated,
        task.reminder
    )

    constructor(
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: LocalDateTime
    ) : this(0, title, description, dueDate, estimated, reminder, false)
}
