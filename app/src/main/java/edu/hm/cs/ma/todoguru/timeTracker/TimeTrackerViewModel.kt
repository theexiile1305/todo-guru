package edu.hm.cs.ma.todoguru.timeTracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import edu.hm.cs.ma.todoguru.database.Time
import edu.hm.cs.ma.todoguru.database.TimeTrackerDatabaseDao
import edu.hm.cs.ma.todoguru.formatTimes
import kotlinx.coroutines.*

class TimeTrackerViewModel(
    val database: TimeTrackerDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var time = MutableLiveData<Time?>()

    private val times = database.getAllTimes()

    val timesString = Transformations.map(times) { times ->
        formatTimes(times, application.resources)
    }

    val startButtonVisible = Transformations.map(time) {
        null == it
    }

    val stopButtonVisible = Transformations.map(time) {
        null != it
    }


    init {
        initializeTime()
    }

    private fun initializeTime() {
        uiScope.launch {
            time.value = getTimeFromDatabase()
        }
    }

    private suspend fun getTimeFromDatabase(): Time? {
        return withContext(Dispatchers.IO) {
            var ti = database.getTime()
            if (ti?.endTimeMilli != ti?.startTimeMilli) {
                ti = null
            }
            ti
        }
    }

    private suspend fun update(time: Time) {
        withContext(Dispatchers.IO) {
            database.update(time)
        }
    }

    private suspend fun insert(time: Time) {
        withContext(Dispatchers.IO) {
            database.insert(time)
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newTime = Time()

            insert(newTime)

            time.value = getTimeFromDatabase()
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldTime = time.value ?: return@launch

            oldTime.endTimeMilli = System.currentTimeMillis()

            update(oldTime)
            time.value = null
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
