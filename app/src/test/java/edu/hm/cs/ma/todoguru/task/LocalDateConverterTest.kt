package edu.hm.cs.ma.todoguru.task

import edu.hm.cs.ma.todoguru.database.LocalDateConverter
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverterTest {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @Test
    fun testToLocalDate() {
        val localDateConverter = LocalDateConverter()
        val localDate = LocalDate.of(2020, 1, 1)
        val localDateString = localDate.format(formatter)
        Assert.assertEquals(localDate, localDateConverter.toDate(localDateString))
    }

    @Test
    fun testToLocalDateString() {
        val localDateConverter = LocalDateConverter()
        val localDate = LocalDate.of(2020, 1, 1)
        val localDateString = localDate.toString()
        Assert.assertEquals(localDateString, localDateConverter.toDateString(localDate))
    }

    @Test
    fun testNullToLocalDate() {
        val localDateConverter = LocalDateConverter()
        Assert.assertNull(localDateConverter.toDateString(null))
    }
}
