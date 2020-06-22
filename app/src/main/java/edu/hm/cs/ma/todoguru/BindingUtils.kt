package edu.hm.cs.ma.todoguru

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@BindingAdapter("localDate")
fun TextView.setLocalDate(localDate: LiveData<LocalDate>) {
    localDate.let {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        text = localDate.value?.format(formatter)
    }
}

@BindingAdapter("localTime")
fun TextView.setLocalTime(localTime: LiveData<LocalTime>) {
    localTime.let {
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        text = localTime.value?.format(formatter)
    }
}

@BindingAdapter("localDateTime")
fun TextView.setLocalDateTime(localDateTime: LiveData<LocalDateTime>) {
    localDateTime.let {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        text = localDateTime.value?.format(formatter) ?: "Set Reminder"
    }
}

@BindingAdapter("category")
fun TextView.setCategory(category: LiveData<String>) {
    category.let {
        text = category.value ?: "Set Category"
    }
}

@BindingAdapter("repeat")
fun TextView.setRepeat(repeat: LiveData<Period>) {
    repeat.let {
        text = when (repeat.value) {
            Period.ofDays(1) -> "every day"
            Period.ofDays(7) -> "every week"
            Period.ofMonths(1) -> "every month"
            else -> "Set task on repeat"
        }
    }
}
