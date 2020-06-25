package edu.hm.cs.ma.todoguru.database

import androidx.room.TypeConverter
import java.time.Period

class PeriodConverter {
    @TypeConverter
    fun toPeriod(periodString: String?): Period? {
        return if (periodString.isNullOrEmpty()) {
            null
        } else {
            Period.parse(periodString)
        }
    }

    @TypeConverter
    fun toPeriodString(period: Period?) = if (period != null) String.format(
        "P%dY%dM%dD",
        period.years,
        period.months,
        period.days
    ) else ""
}
