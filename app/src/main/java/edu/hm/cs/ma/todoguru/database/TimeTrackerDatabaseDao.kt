package edu.hm.cs.ma.todoguru.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimeTrackerDatabaseDao {

    @Insert
    fun insert(night: Time)

    @Update
    fun update(night: Time)

    @Query("SELECT * from time_table WHERE timeId = :key")
    fun get(key: Long): Time?

    @Query("DELETE FROM time_table")
    fun clear()

    @Query("SELECT * FROM time_table ORDER BY timeId DESC")
    fun getAllTimes(): LiveData<List<Time>>

    @Query("SELECT * FROM time_table WHERE taskId = :key ORDER BY timeId DESC")
    fun getTimesOfTask(key: Long): LiveData<List<Time>>

    @Query("SELECT * FROM time_table ORDER BY timeId DESC LIMIT 1")
    fun getTime(): Time?
}
