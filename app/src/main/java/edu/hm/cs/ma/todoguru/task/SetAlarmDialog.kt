package edu.hm.cs.ma.todoguru.task

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.AlarmClock
import edu.hm.cs.ma.todoguru.R
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class SetAlarmDialog(
    context: Context,
    private val intent: Intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
        Timber.d("The SetAlarmDialog that allows the user to set an alarm")
        putExtra(AlarmClock.EXTRA_MESSAGE, context.getString(R.string.set_alarm))
        putExtra(AlarmClock.EXTRA_SKIP_UI, true)
    }
) : TimePickerDialog(
    context,
    OnTimeSetListener { _, hourOfDay, minute ->
        val currentDay = LocalDate.now().dayOfWeek.value
        intent.putExtra(AlarmClock.EXTRA_DAYS, currentDay)
        intent.putExtra(AlarmClock.EXTRA_HOUR, hourOfDay)
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minute)
    },
    LocalTime.now(ZoneId.of("Europe/Berlin")).hour,
    LocalTime.now(ZoneId.of("Europe/Berlin")).minute,
    true
) {
    override fun onClick(dialog: DialogInterface?, which: Int) {
        super.onClick(dialog, which)
        if (which == BUTTON_POSITIVE) {
            context.startActivity(intent)
        }
    }
}
