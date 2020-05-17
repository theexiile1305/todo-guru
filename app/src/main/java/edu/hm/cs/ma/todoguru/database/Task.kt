package edu.hm.cs.ma.todoguru.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "due Date")
    var dueDate: String,
    @ColumnInfo(name = "estimated Time")
    var estimated: String,
    @ColumnInfo(name = "reminder")
    var reminder: String
)
