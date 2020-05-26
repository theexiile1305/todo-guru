package edu.hm.cs.ma.todoguru.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDatabaseDao {

    @Query("SELECT * FROM task_table WHERE not done")
    fun getAllTask(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE not done ORDER BY `estimated Time` ASC")
    fun getAllTasks(): List<Task>

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)
}
