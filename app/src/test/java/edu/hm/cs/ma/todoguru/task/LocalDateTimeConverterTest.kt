package edu.hm.cs.ma.todoguru.task

import edu.hm.cs.ma.todoguru.database.LocalDateTimeConverter
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverterTest {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @Test
    fun testToLocalDateTIme() {
        val localDateTimeConverter = LocalDateTimeConverter()
        val localDateTime = LocalDateTime.of(2020, 1, 1, 1, 1)
        val localDateString = localDateTime.format(formatter)
        Assert.assertEquals(localDateTime, localDateTimeConverter.toLocalDateTime(localDateString))
    }

    @Test
    fun testNullToLocalDateTIme() {
        val localDateTimeConverter = LocalDateTimeConverter()
        Assert.assertNull(localDateTimeConverter.toLocalDateTime(null))
    }

    @Test
    fun testToLocalDateTimeString() {
        val localDateTimeConverter = LocalDateTimeConverter()
        val localDateTime = LocalDateTime.of(2020, 1, 1, 1, 1)
        val localDateTimeString = localDateTime.toString()
        Assert.assertEquals(
            localDateTimeString,
            localDateTimeConverter.toLocalDateTimeString(localDateTime)
        )
    }

    @Test
    fun testNullToLocalDateTime() {
        val localDateTimeConverter = LocalDateTimeConverter()
        Assert.assertNull(localDateTimeConverter.toLocalDateTimeString(null))
    }
}
