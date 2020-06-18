package edu.hm.cs.ma.todoguru.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Time::class], version = 1, exportSchema = false)
abstract class TimeTrackerDatabase : RoomDatabase() {

    abstract val timeTrackerDatabaseDao: TimeTrackerDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TimeTrackerDatabase? = null

        fun getInstance(context: Context): TimeTrackerDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            TimeTrackerDatabase::class.java,
                            "time_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
