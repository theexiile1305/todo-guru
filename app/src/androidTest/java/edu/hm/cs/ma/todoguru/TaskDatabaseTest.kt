package edu.hm.cs.ma.todoguru

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {

    private lateinit var taskDao: TaskDatabaseDao
    private lateinit var database: TaskDatabase
    private lateinit var task: Task

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
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertTask() {
        task = Task(0, "Title", "Description", date("20/05/2020"), 5, "", false)
        taskDao.insert(task)
        assertEquals(true, taskDao.getAllTasks().isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    private fun date(date: String): LocalDate {
        val formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy")

        return LocalDate.parse(date, formatter)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTask() {
        taskDao.delete(task)
        assertEquals(true, taskDao.getAllTasks().isEmpty())
    }
}
