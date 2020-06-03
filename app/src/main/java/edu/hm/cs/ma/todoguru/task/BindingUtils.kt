package edu.hm.cs.ma.todoguru.task

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("localDateTime")
fun TextView.setTimerName(localDateTime: LocalDateTime) {
    localDateTime.let {
        text = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }
}
