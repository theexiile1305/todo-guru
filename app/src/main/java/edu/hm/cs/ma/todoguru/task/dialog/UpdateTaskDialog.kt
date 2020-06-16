package edu.hm.cs.ma.todoguru.task.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.UpdateTaskDialogBinding
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import kotlinx.android.synthetic.main.update_task_dialog.topAppBar
import java.time.LocalDate
import java.time.LocalDateTime

class UpdateTaskDialog : Fragment() {

    private lateinit var mContext: Context
    private lateinit var binding: UpdateTaskDialogBinding
    private lateinit var viewModel: TaskViewModel
    private val args: UpdateTaskDialogArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.insert_task_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = requireActivity().run {
            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        }

        binding.apply {
            viewModel = this@UpdateTaskDialog.viewModel
            lifecycleOwner = this@UpdateTaskDialog
        }

        viewModel.apply {
            setDefaultUpdateValue(args.task)
            createTaskEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) updateTask()
                }
            )
            addReminderEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) openSetReminderDialog()
                }
            )
            addDueDateEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) openSetDueDateDialog(dueDate.value!!)
                }
            )
        }

        topAppBar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    /** DatePickerDialog expects the old Calender class, we are currently using the new
     * @see LocalDate which needs a transformation on the month field:
     * e. g. months of Calender class are from 0-11 and in LocalDate from 1-12
     **/
    private fun openSetDueDateDialog(dueDate: LocalDate) {
        DatePickerDialog(
            mContext,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.dueDate.value = LocalDate.of(year, month + 1, dayOfMonth)
            },
            dueDate.year,
            dueDate.monthValue - 1,
            dueDate.dayOfMonth
        ).show()
    }

    private fun updateTask() {
        viewModel.apply {
            updateTask(
                title.value!!,
                description.value!!,
                dueDate.value!!,
                estimated.value!!,
                LocalDateTime.of(reminderDate.value, reminderTime.value)
            )
        }
        findNavController().popBackStack()
    }

    private fun openSetReminderDialog() {
        findNavController().navigate(InsertTaskDialogDirections.actionInsertTaskDialogToSetReminderDialog())
    }
}
