package edu.hm.cs.ma.todoguru

import android.app.Application
import timber.log.Timber

class ToDoGuruApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
