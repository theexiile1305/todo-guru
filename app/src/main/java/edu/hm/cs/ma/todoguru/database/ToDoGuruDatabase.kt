package edu.hm.cs.ma.todoguru.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class, Category::class], version = 2, exportSchema = false)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class)
abstract class ToDoGuruDatabase : RoomDatabase() {
    abstract val taskDatabaseDao: TaskDatabaseDao
    abstract val categoryDatabaseDao: CategoryDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoGuruDatabase? = null

        fun getInstance(context: Context): ToDoGuruDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoGuruDatabase::class.java,
                    "database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}