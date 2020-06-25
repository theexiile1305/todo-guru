package edu.hm.cs.ma.todoguru.task.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.hm.cs.ma.todoguru.R
import edu.hm.cs.ma.todoguru.database.Category
import edu.hm.cs.ma.todoguru.database.ToDoGuruDatabase
import edu.hm.cs.ma.todoguru.databinding.CategoryListFragmentBinding

class CategoryListFragment : CategoryAdapter.Listener, Fragment() {

    private lateinit var binding: CategoryListFragmentBinding
    private lateinit var viewModel: CategoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.category_list_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = requireActivity().run {
            val dataSource = ToDoGuruDatabase.getInstance(this).categoryDatabaseDao
            val viewModelFactory = CategoryListViewModel.Factory(dataSource, application)
            ViewModelProvider(this, viewModelFactory).get(CategoryListViewModel::class.java)
        }

        val adapter = CategoryAdapter(this)
        binding.apply {
            viewModel = this@CategoryListFragment.viewModel
            lifecycleOwner = this@CategoryListFragment
            categoryList.adapter = adapter

            createTaskButton.setOnClickListener { openInsertDialog() }
        }

        viewModel.apply {
            categories.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.submitList(it)
                }
            )
        }
    }

    private fun openInsertDialog() {
        findNavController().navigate(CategoryListFragmentDirections.actionCategoryListFragmentToInsertCategoryDialog())
    }

    override fun onDeleteClick(category: Category) {
        findNavController().navigate(
            CategoryListFragmentDirections.actionCategoryListFragmentToDeleteCategoryDialog(
                category
            )
        )
    }
}
