package edu.hm.cs.ma.todoguru.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: Task)
}