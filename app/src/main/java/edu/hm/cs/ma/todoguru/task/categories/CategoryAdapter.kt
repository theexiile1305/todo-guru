package edu.hm.cs.ma.todoguru.task.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.hm.cs.ma.todoguru.database.Category
import edu.hm.cs.ma.todoguru.databinding.CategoryViewHolderBinding
import timber.log.Timber

class CategoryAdapter(
    private val listener: Listener
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(
    CategoryDiffCallBack()
) {
    interface Listener {
        fun onDeleteClick(category: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), listener)

    class ViewHolder private constructor(private val binding: CategoryViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryViewHolderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(category: Category, listener: Listener) {
            binding.category = category
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            Timber.d("Check if two items has the same data")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            Timber.d("Used to replace two items")
            return oldItem == newItem
        }
    }
}
