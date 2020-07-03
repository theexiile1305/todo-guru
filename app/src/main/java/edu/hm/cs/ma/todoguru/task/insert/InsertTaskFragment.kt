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
import timber.log.Timber

class InsertTaskFragment : TaskFragment() {

    private lateinit var binding: InsertTaskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("Navigate to insert task fragment")
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
                Timber.d("Insert SetReminder")
                updateValues()
                openSetReminderDialog()
            }
            chipSetCategory.setOnClickListener {
                Timber.d("Insert SetCetegory")
                updateValues()
                openSetCategoryDialog()
            }
            chipSetSubTask.setOnClickListener {
                Timber.d("Insert SetSubTask")
                updateValues()
                openSetSubTaskDialog()
            }
            chipSetRepeat.setOnClickListener {
                Timber.d("Insert SetRepeat")
                updateValues()
                openSetRepeatDialog()
            }

            switchButton.setOnCheckedChangeListener { _, _ ->
                updateValues()
            }

            insertTaskButton.setOnClickListener {
                Timber.d("Insert Task")
                if (validateUserInput()) {
                    updateValues()
                    this@InsertTaskFragment.viewModel.insertTask()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun openSetCategoryDialog() {
        Timber.d("Navigate to SetCategoryDialog")
        findNavController().navigate(InsertTaskFragmentDirections.actionInsertTaskFragmentToSetCategoryDialog())
    }

    override fun openSetReminderDialog() {
        Timber.d("Navigate to SetReminderFragment")
        findNavController().navigate(InsertTaskFragmentDirections.actionInsertTaskFragmentToSetReminderFragment())
    }

    private fun openSetSubTaskDialog() {
        Timber.d("Navigate to SubTaskListFragment")
        findNavController().navigate(InsertTaskFragmentDirections.actionInsertTaskFragmentToSubTaskListFragment())
    }

    private fun openSetRepeatDialog() {
        Timber.d("Navigate to SetRepeatDialog")
        findNavController().navigate(InsertTaskFragmentDirections.actionInsertTaskFragmentToSetRepeatDialog())
    }

    override fun getTitle() = binding.title

    override fun getDescription() = binding.description

    override fun getEstimated() = binding.estimated

    override fun getPriority() = binding.switchButton
}
