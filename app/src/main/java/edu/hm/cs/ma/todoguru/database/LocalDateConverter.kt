package edu.hm.cs.ma.todoguru.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            val formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            LocalDate.parse(dateString, formatter)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString()
    }
}
