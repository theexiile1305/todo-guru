package edu.hm.cs.ma.todoguru.task.subTask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.hm.cs.ma.todoguru.database.SubTask
import edu.hm.cs.ma.todoguru.databinding.SubtaskViewHolderBinding

class SubTaskAdapter(
    private val listener: Listener
) : ListAdapter<SubTask, SubTaskAdapter.ViewHolder>(
    SubTaskDiffCallBack()
) {
    interface Listener {
        fun onDeleteClick(SubTask: SubTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    class ViewHolder private constructor(private val binding: SubtaskViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SubtaskViewHolderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(SubTask: SubTask, listener: Listener) {
            binding.subTask = SubTask
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class SubTaskDiffCallBack : DiffUtil.ItemCallback<SubTask>() {

        override fun areItemsTheSame(oldItem: SubTask, newItem: SubTask): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SubTask, newItem: SubTask): Boolean {
            return oldItem == newItem
        }
    }
}
