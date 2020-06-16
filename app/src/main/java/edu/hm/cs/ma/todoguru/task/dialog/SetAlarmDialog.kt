package edu.hm.cs.ma.todoguru.task.dialog

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.AlarmClock
import edu.hm.cs.ma.todoguru.R
import java.time.Duration
import java.time.LocalTime

class SetAlarmDialog(
    context: Context,
    private val intent: Intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
        putExtra(AlarmClock.EXTRA_MESSAGE, context.getString(R.string.set_alarm))
        putExtra(AlarmClock.EXTRA_SKIP_UI, true)
    }
) : TimePickerDialog(
    context,
    OnTimeSetListener { _, hourOfDay, minute ->
        val duration = Duration.ofSeconds(LocalTime.of(hourOfDay, minute).toSecondOfDay().toLong())
        intent.putExtra(AlarmClock.EXTRA_LENGTH, duration.seconds.toInt())
    },
    LocalTime.now().hour - 1,
    LocalTime.now().minute,
    true
) {
    override fun onClick(dialog: DialogInterface?, which: Int) {
        super.onClick(dialog, which)
        if (which == BUTTON_POSITIVE) {
            context.startActivity(intent)
        }
    }
}
