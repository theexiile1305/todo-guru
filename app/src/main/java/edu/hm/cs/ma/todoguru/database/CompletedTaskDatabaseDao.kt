package edu.hm.cs.ma.todoguru.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CompletedTaskDatabaseDao {

    @Query("SELECT * FROM task_table WHERE done")
    fun getAllTask(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE done ORDER BY `estimated Time` ASC")
    fun getAllTasks(): List<Task>

    @Insert
    fun insert(task: Task)

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun get(id: Long): Task?

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}
