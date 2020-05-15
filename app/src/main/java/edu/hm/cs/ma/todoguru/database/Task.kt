package edu.hm.cs.ma.todoguru.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L
)