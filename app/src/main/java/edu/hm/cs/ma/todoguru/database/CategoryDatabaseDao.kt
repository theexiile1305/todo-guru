package edu.hm.cs.ma.todoguru.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDatabaseDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): LiveData<List<Category>>

    @Insert
    fun insert(category: Category)

    @Query("SELECT * FROM category WHERE id = :id")
    fun get(id: Long): LiveData<Category>

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)
}
