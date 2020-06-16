package edu.hm.cs.ma.todoguru.task.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.SetReminderDialogBinding
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import kotlinx.android.synthetic.main.set_reminder_dialog.topAppBar
import java.time.LocalDate
import java.time.LocalTime

class SetReminderDialog : Fragment() {

    private lateinit var mContext: Context
    private lateinit var binding: SetReminderDialogBinding
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
        binding = DataBindingUtil.inflate(inflater, R.layout.set_reminder_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = requireActivity().run {
            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        }

        binding.apply {
            viewModel = this@SetReminderDialog.viewModel
            lifecycleOwner = this@SetReminderDialog
        }

        viewModel.apply {
            addReminderDateEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) openSetReminderDateDialog(reminderDate.value!!)
                }
            )
            addReminderTimeEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) openSetReminderTimeDialog(reminderTime.value!!)
                }
            )
            insertReminderEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it && checkDueDateAfterReminder()) findNavController().popBackStack()
                }
            )
        }

        topAppBar.setNavigationOnClickListener {
            if (checkDueDateAfterReminder()) findNavController().popBackStack()
        }
    }

    /** DatePickerDialog expects the old Calender class, we are currently using the new
     * @see LocalDate which needs a transformation on the month field:
     * e. g. months of Calender class are from 0-11 and in LocalDate from 1-12
     **/
    private fun openSetReminderDateDialog(reminderDate: LocalDate) {
        DatePickerDialog(
            mContext,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.reminderDate.value = LocalDate.of(year, month + 1, dayOfMonth)
            },
            reminderDate.year,
            reminderDate.monthValue - 1,
            reminderDate.dayOfMonth
        ).show()
    }

    private fun openSetReminderTimeDialog(reminderTime: LocalTime) {
        TimePickerDialog(
            mContext,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                viewModel.reminderTime.value = LocalTime.of(hourOfDay, minute)
            },
            reminderTime.hour,
            reminderTime.minute,
            false
        ).show()
    }

    private fun checkDueDateAfterReminder(): Boolean {
        val isValid = viewModel.let {
            it.dueDate.value!!.isAfter((it.reminderDate.value))
        }
        if (!isValid)
            Toast.makeText(mContext, "Due date has to be after reminder", Toast.LENGTH_SHORT)
                .show()
        return isValid
    }
}
