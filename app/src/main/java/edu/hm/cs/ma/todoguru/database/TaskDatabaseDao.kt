package edu.hm.cs.ma.todoguru.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: Task)

    @Query("SELECT * FROM task_table ORDER BY `estimated Time` ASC")
    fun getAllTasks(): List<Task>
}
