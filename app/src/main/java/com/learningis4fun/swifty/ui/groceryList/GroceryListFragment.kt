package com.learningis4fun.swifty.ui.groceryList

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learningis4fun.swifty.R
import com.learningis4fun.swifty.data.GroceryListItem
import com.learningis4fun.swifty.data.SortByOption
import com.learningis4fun.swifty.databinding.FragmentGroceryListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GroceryListFragment : Fragment(R.layout.fragment_grocery_list),
    GroceryListAdapter.GroceryListItemClickListener, Toolbar.OnMenuItemClickListener,
    SortByFragment.SortByFragmentListener {

    val viewModel: GroceryListViewModel by viewModels()
    private lateinit var groceryListItemAdapter: GroceryListAdapter
    private var binding: FragmentGroceryListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroceryListBinding.bind(view)
        groceryListItemAdapter = GroceryListAdapter(this)
        setupRecyclerView()
        registerObservers()
        registerEventsListener()
        binding?.apply {
            toolbar.apply {
                setNavigationOnClickListener { findNavController().popBackStack() }
                setNavigationIcon(R.drawable.ic_arrow_back_white)
                title = viewModel.toolbarTitle ?: "No name"
            }
            toolbar.inflateMenu(R.menu.menu_fragment_grocery_list)
            toolbar.setOnMenuItemClickListener(this@GroceryListFragment)
            addNewItem.setOnClickListener {
                viewModel.onAddGroceryListItemClick()
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.apply {
            groceryListRecyclerView.apply {
                setHasFixedSize(false)
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(requireContext())
                adapter = groceryListItemAdapter
            }
        }
    }

    private fun registerObservers() {
        viewModel.groceries().observe(viewLifecycleOwner) {
            groceryListItemAdapter.submitList(it)
        }

        viewModel.totalPriceForGroceries().observe(viewLifecycleOwner) {
            hideTotalText(it)
        }

        viewModel.hideCategories().observe(viewLifecycleOwner) {
            binding?.apply {
                toolbar.apply {
                    menu.findItem(R.id.action_sort_by).apply {
                        isVisible = it
                        isEnabled = it
                    }
                    menu.findItem(R.id.action_hide_categories).isChecked = it

                }
            }
        }
    }

    private fun registerEventsListener() {
        lifecycleScope.launchWhenStarted {
            viewModel.groceryListFragmentEvents.collect { event ->
                when (event) {
                    is GroceryListViewModel.GroceryListFragmentEvents.NavigateToAddEditGroceryListItemScreen -> {
                        val action =
                            GroceryListFragmentDirections.actionGroceryListFragmentToAddEditGroceryListFragment(
                                event.groceryListItem,
                                event.collectionId
                            )
                        findNavController().navigate(action)
                    }
                    is GroceryListViewModel.GroceryListFragmentEvents.SendMessageToScreen -> {
                        Toast.makeText(requireContext(), event.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun hideTotalText(price: String) {
        binding?.apply {
            totalPriceTitle.visibility = if (price.isBlank()) View.INVISIBLE else View.VISIBLE
            totalPrice.visibility = if (price.isBlank()) View.INVISIBLE else View.VISIBLE
            totalPriceView.visibility = if (price.isBlank()) View.INVISIBLE else View.VISIBLE
            totalPrice.text = price
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onGroceryListItemClick(groceryListItem: GroceryListItem) {
        viewModel.onGroceryListItemClick(groceryListItem)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_sort_by -> {
                val dialog = SortByFragment(this, viewModel.sortByOptions())
                dialog.show(childFragmentManager, "sort by")
                true
            }
            R.id.action_hide_categories -> {
                val isChecked = !item.isChecked
                item.isChecked = isChecked
                viewModel.onHideCategoriesClick(isChecked)
                true
            }

            else -> false
        }

    }

    override fun onSortByClick(sortByOption: SortByOption) {
        viewModel.onSortByOptionClick(sortByOption)
    }
}