package edu.hm.cs.ma.todoguru.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDatabaseDao {

    @Query("SELECT * FROM task_table")
    fun getAllTask(): LiveData<List<Task>>

    @Insert
    fun insert(task: Task)
}
