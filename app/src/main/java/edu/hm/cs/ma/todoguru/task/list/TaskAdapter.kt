package edu.hm.cs.ma.todoguru.task.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.databinding.TaskViewHolderBinding
import timber.log.Timber

class TaskAdapter(
    private val listener: Listener
) : ListAdapter<Task, TaskAdapter.ViewHolder>(
    TaskDiffCallBack()
) {

    interface Listener {
        fun onCheckBoxClick(wrapper: TaskWrapper)
        fun onUpdateClick(task: Task)
        fun onViewTaskClick(view: View, task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    class ViewHolder private constructor(private val binding: TaskViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskViewHolderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(task: Task, listener: Listener) {
            binding.wrapper = TaskWrapper(task)
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class TaskDiffCallBack : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            Timber.d("Check if two items has the same data")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            Timber.d("Used to replace two items")
            return oldItem == newItem
        }
    }
}
