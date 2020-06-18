package edu.hm.cs.ma.todoguru.timeTracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.TimeTrackerDatabase

class TimeTrackerFragment : Fragment() {

/*    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentTimeTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TimeTrackerDatabase.getInstance(application).timeTrackerDatabaseDao

        val viewModelFactory = TimeTrackerViewModelFactory(dataSource, application)

        val timeTrackerViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(TimeTrackerViewModel::class.java)

        binding.timeTrackerViewModel = timeTrackerViewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }*/
}
