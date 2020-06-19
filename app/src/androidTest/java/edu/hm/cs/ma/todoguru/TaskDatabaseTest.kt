package edu.hm.cs.ma.todoguru

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {

    private lateinit var taskDao: TaskDatabaseDao
    private lateinit var database: TaskDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = database.taskDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.clearAllTables()
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertTask() {
        val task =
            Task(0, "Title", "Description", LocalDate.now(), 5, LocalDateTime.now(), false)
        taskDao.insert(task)
        assertEquals(true, taskDao.getAllTasks().isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun deleteTask() {
        val task =
            Task(0, "Title", "Description", LocalDate.now(), 5, LocalDateTime.now(), false)
        taskDao.delete(task)
        assertEquals(true, taskDao.getAllTasks().isEmpty())
    }
}
