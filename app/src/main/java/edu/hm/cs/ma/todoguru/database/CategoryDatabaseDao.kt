package edu.hm.cs.ma.todoguru.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDatabaseDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): LiveData<List<Category>>

    @Insert
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)
}
