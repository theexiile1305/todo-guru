package edu.hm.cs.ma.todoguru.task.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.databinding.TaskListFragmentBinding
import edu.hm.cs.ma.todoguru.task.SetAlarmDialog
import kotlinx.android.synthetic.main.task_list_fragment.topAppBar
import timber.log.Timber

class TaskListFragment : TaskAdapter.Listener, Fragment() {

    private lateinit var binding: TaskListFragmentBinding
    private lateinit var viewModel: TaskListViewModel

    private val selectedTasks = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.task_list_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(topAppBar)

        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).taskDatabaseDao
            val viewModelFactory = TaskListViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(TaskListViewModel::class.java)
        }

        val adapter = TaskAdapter(this)
        binding.apply {
            viewModel = this@TaskListFragment.viewModel
            lifecycleOwner = this@TaskListFragment
            tasksList.adapter = adapter

            createTaskButton.setOnClickListener { openInsertDialog() }

            viewProductivityButton.setOnClickListener { openViewProductivity() }
        }

        viewModel.apply {
            tasks.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.submitList(it)
                }
            )
            markTaskDoneEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) selectedTasks.clear()
                }
            )
            deleteTaskEvent.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) selectedTasks.clear()
                }
            )
        }
    }

    override fun onViewTaskClick(view: View, task: Task) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.item_menu, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.view_task -> openViewTaskFragment(task)
                    R.id.set_alarm -> openSetAlarmFragment()
                    else -> false
                }
            }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.d("Shows all the options that the user can choose from the MenuItem")
        when (item.itemId) {
            R.id.mark_tasks_as_done -> markTaskAsDone()
            R.id.delete_tasks -> deleteTasks()
            R.id.completed_tasks -> navigateToCompletedTasks()
            R.id.recommend_app -> recommendApp()
            R.id.privacy_policy -> openPrivacyPolicy()
            R.id.contact_developers -> contactDevelopers()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCheckBoxClick(wrapper: TaskWrapper) {
        val task = wrapper.task
        if (selectedTasks.contains(task)) selectedTasks.remove(task)
        else selectedTasks.add(task)
        wrapper.isSelected = true
    }

    override fun onUpdateClick(task: Task) {
        findNavController().navigate(
            TaskListFragmentDirections.actionTaskListFragmentToUpdateTaskFragment(task)
        )
    }

    private fun markTaskAsDone() = viewModel.markTasksAsDone(selectedTasks)

    private fun navigateToCompletedTasks() {
        findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToCompletedTaskListFragment())
    }

    private fun deleteTasks() {
        if (selectedTasks.isNotEmpty()) {
            openDeleteDialog()
        }
    }

    private fun openInsertDialog() {
        findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToInsertTaskFragment())
    }

    private fun openDeleteDialog() {
        findNavController().navigate(
            TaskListFragmentDirections.actionTaskListFragmentToDeleteDialog(
                selectedTasks.toTypedArray()
            )
        )
    }

    private fun openViewTaskFragment(task: Task): Boolean {
        findNavController()
            .navigate(TaskListFragmentDirections.actionTaskListFragmentToViewTaskFragment(task))
        return true
    }

    private fun openSetAlarmFragment(): Boolean {
        SetAlarmDialog(requireContext()).show()
        return true
    }

    private fun openViewProductivity(): Boolean {
        findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToProductivityFragment())
        return true
    }

    private fun recommendApp() {
        Timber.d("Recommend the app")
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setChooserTitle(getString(R.string.recommend_app))
            .setText(getString(R.string.recommend_app_message))
            .setType("text/html")
            .intent
            .let { startActivity(it) }
    }

    private fun openPrivacyPolicy() {
        Timber.d("Show our privacy policy")
        findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToPrivacyPolicyFragment())
    }

    private fun contactDevelopers() = startActivity(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(getString(R.string.github_repository_issues))
        )
    )
}
