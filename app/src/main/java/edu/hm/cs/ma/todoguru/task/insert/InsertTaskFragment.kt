package edu.hm.cs.ma.todoguru.task.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.databinding.InsertTaskFragmentBinding
import edu.hm.cs.ma.todoguru.task.TaskFragment

class InsertTaskFragment : TaskFragment() {

    private lateinit var binding: InsertTaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.insert_task_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@InsertTaskFragment.viewModel
            lifecycleOwner = this@InsertTaskFragment

            dueDate.setOnClickListener { openSetDueDateDialog(this@InsertTaskFragment.viewModel.dueDate.value!!) }
            chipSetReminder.setOnClickListener {
                updateValues()
                openSetReminderDialog()
            }
            insertTaskButton.setOnClickListener {
                if (validateUserInput()) {
                    updateValues()
                    this@InsertTaskFragment.viewModel.insertTask()
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun openSetReminderDialog() {
        findNavController().navigate(InsertTaskFragmentDirections.actionInsertTaskFragmentToSetReminderFragment())
    }

    override fun getTitle() = binding.title

    override fun getDescription() = binding.description

    override fun getEstimated() = binding.estimated
}
