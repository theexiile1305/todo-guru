package edu.hm.cs.ma.todoguru.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SubTaskDatabaseDao {

    @Query("SELECT * FROM sub_task")
    fun getAllSubTask(): LiveData<List<SubTask>>

    @Insert
    fun insert(subTask: SubTask)

    @Delete
    fun delete(subTask: SubTask)
}
