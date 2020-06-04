package edu.hm.cs.ma.todoguru.database

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    @TypeConverter
    fun toLocalDateTime(localDateTimeString: String?): LocalDateTime? {
        return if (localDateTimeString == null) {
            null
        } else {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            LocalDateTime.parse(localDateTimeString, formatter)
        }
    }

    @TypeConverter
    fun toLocalDateTimeString(localDateTime: LocalDateTime?): String? {
        return localDateTime?.toString()
    }
}
