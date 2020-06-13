package edu.hm.cs.ma.todoguru

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.hm.cs.ma.todoguru.notification.ReminderNotification

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, ReminderNotification::class.java))
    }
}
