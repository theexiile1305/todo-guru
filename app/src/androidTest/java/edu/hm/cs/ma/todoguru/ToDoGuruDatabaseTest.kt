package edu.hm.cs.ma.todoguru

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class ToDoGuruDatabaseTest {

    private lateinit var taskDao: TaskDatabaseDao
    private lateinit var toDoGuruDatabase: ToDoGuruDatabase
    val date = "20/05/2020"

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        toDoGuruDatabase = Room.inMemoryDatabaseBuilder(context, ToDoGuruDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        taskDao = toDoGuruDatabase.taskDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        toDoGuruDatabase.clearAllTables()
        toDoGuruDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertTask() {
        val task =
            Task(
                0,
                "Title",
                "Description",
                date(date),
                5,
                LocalDateTime.now(),
                "",
                emptyList(),
                null,
                false
            )
        taskDao.insert(task)
        assertEquals(true, taskDao.getAllTasks().isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun updateTask() {
        val task =
            Task(
                0,
                "Title",
                "Description",
                date("20/05/2020"),
                5,
                LocalDateTime.now(),
                "",
                emptyList(),
                null,
                false
            )
        taskDao.insert(task)
        taskDao.update(task)
        assertEquals(true, taskDao.getAllTasks().isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun deleteTask() {
        val task =
            Task(
                0,
                "Title",
                "Description",
                date(date),
                5,
                LocalDateTime.now(),
                "",
                emptyList(),
                null,
                false
            )
        taskDao.delete(task)
        assertTrue(taskDao.getAllTasks().isEmpty())
    }
}
