package edu.hm.cs.ma.todoguru.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.SetReminderFragmentBinding
import java.time.LocalDate
import java.time.LocalTime

class SetReminderFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var binding: SetReminderFragmentBinding
    private lateinit var viewModel: TaskViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.set_reminder_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = requireActivity().run {
            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        }

        binding.apply {
            viewModel = this@SetReminderFragment.viewModel
            lifecycleOwner = this@SetReminderFragment

            reminderDate.setOnClickListener { openSetReminderDateDialog() }
            reminderTime.setOnClickListener { openSetReminderTimeDialog() }
            setReminderButton.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun openSetReminderDateDialog() {
        val currentDate = LocalDate.now()
        DatePickerDialog(
            mContext,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.reminderDate.value = LocalDate.of(year, month + 1, dayOfMonth)
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        ).show()
    }

    private fun openSetReminderTimeDialog() {
        val currentTime = LocalTime.now()
        TimePickerDialog(
            mContext,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                viewModel.reminderTime.value = LocalTime.of(hourOfDay, minute)
            },
            currentTime.hour,
            currentTime.minute,
            false
        ).show()
    }
}
