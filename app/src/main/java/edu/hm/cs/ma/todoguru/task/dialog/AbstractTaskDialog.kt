// package edu.hm.cs.ma.todoguru.task.dialog
//
// import android.app.DatePickerDialog
// import android.content.Context
// import android.os.Bundle
// import android.view.View
// import android.view.ViewGroup
// import android.widget.Toast
// import androidx.fragment.app.DialogFragment
// import androidx.lifecycle.ViewModelProvider
// import com.google.android.material.chip.Chip
// import com.google.android.material.textfield.TextInputEditText
// import edu.hm.cs.ma.todoguru.R
// import edu.hm.cs.ma.todoguru.database.TaskDatabase
// import edu.hm.cs.ma.todoguru.task.list.TaskListViewModel
// import java.time.LocalDate
// import java.time.LocalDateTime
// import java.time.format.DateTimeFormatter
// import java.time.format.FormatStyle
//
// abstract class AbstractTaskDialog : DialogFragment() {
//
//    protected lateinit var mContext: Context
//    protected lateinit var listViewModel: TaskListViewModel // TODO change in own viewModel
//    protected lateinit var title: TextInputEditText
//    protected lateinit var description: TextInputEditText
//    protected lateinit var estimated: TextInputEditText
//    private lateinit var dueDateText: TextInputEditText
//    protected  lateinit var reminderChip: Chip
//
//    protected lateinit var dueDate: LocalDate
//    protected  lateinit var reminder: LocalDateTime
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mContext = context
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (dialog != null) {
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog!!.window?.setLayout(width, height)
//        }
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        listViewModel = requireActivity().run {
//            val dataSource = TaskDatabase.getInstance(this).taskDatabaseDao
//            val viewModelFactory =
//                TaskListViewModel.Factory(
//                    dataSource,
//                    application
//                )
//            ViewModelProvider(this, viewModelFactory)
//                .get(TaskListViewModel::class.java)
//        }
//    }
//
//    protected fun setupView(view: View) {
//        title = view.findViewById(R.id.title)
//        description = view.findViewById(R.id.description)
//        dueDateText = view.findViewById(R.id.dueDate)
//        dueDateText.isFocusable = false
//        estimated = view.findViewById(R.id.estimated)
//        reminderChip = view.findViewById(R.id.chip_set_reminder)
//    }
//
//    protected fun determineDueDate() {
//        dueDateText.setOnClickListener {
//            // DatePickerDialog expects the old Calender class, we are currently using the new
//            // java.time.LocalDate which needs a transformation on the month field:
//            // e. g. months of Calender class are from 0-11 and in LocalDate from 1-12
//            DatePickerDialog(
//                mContext,
//                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//                    dueDate = LocalDate.of(year, month + 1, dayOfMonth)
//                   // setDueDateText(dueDate)
//                },
//                dueDate.year,
//                dueDate.monthValue - 1,
//                dueDate.dayOfMonth
//            ).show()
//        }
//    }
//
//    protected fun validateUserInput(): Boolean {
//        val validation = HashSet<Boolean>()
//
//        validation.add(validate(title, "The title is required"))
//        validation.add(validate(description, "The description is required"))
//        validation.add(validate(estimated, "The estimation is required"))
//        validation.add(validate(dueDateText, "The due date is required"))
//        if(reminderChip.text.toString().isNotEmpty())
//            validation.add(checkDueDateAfterReminder())
//        return validation.remove(true) && validation.isEmpty()
//    }
//
//    private fun checkDueDateAfterReminder(): Boolean {
//        val isValid = dueDate.isAfter(reminder.toLocalDate())
//        if (!isValid)
//            Toast.makeText(mContext, "Due date has to be after reminder", Toast.LENGTH_SHORT)
//                .show()
//        return isValid
//    }
//
//    private fun validate(field: TextInputEditText, error: String): Boolean {
//        if (field.text.toString().isEmpty()) {
//            field.error = error
//            return false
//        }
//        return true
//    }
// }
