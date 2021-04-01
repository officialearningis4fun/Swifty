package com.learningis4fun.swifty.ui.collection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learningis4fun.swifty.R
import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.data.util.Response
import com.learningis4fun.swifty.databinding.FragmentCollectionBinding
import com.learningis4fun.swifty.util.LineDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CollectionFragment : Fragment(R.layout.fragment_collection),
    CollectionAdapter.GroceryCollectionClickListener {

    val viewModel: CollectionViewModel by viewModels()
    private lateinit var collectionAdapter: CollectionAdapter
    private var binding: FragmentCollectionBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCollectionBinding.bind(view)

        collectionAdapter = CollectionAdapter(this)
        setupRecyclerView()

        registerEventsListener()
        registerObservers()

        setButtonsClickListeners()
    }

    override fun onCollectionClick(collection: Collection) {
        viewModel.onCollectionClick(collection)
    }

    override fun onDeleteCollectionClick(collection: Collection) {
        viewModel.onDeleteCollection(collection)
    }

    override fun onRenameCollectionClick(collection: Collection) {
        viewModel.onRenameCollection(collection)
    }

    private fun setupRecyclerView() {
        binding?.apply {
            collectionRecyclerView.apply {
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = collectionAdapter
                addItemDecoration(LineDecorator(requireContext()).getLineDecoration())
            }
        }
    }

    private fun setButtonsClickListeners() {
        binding?.apply {
            addCollectionBtn.setOnClickListener {
                viewModel.onAddCollectionClick()
            }
        }
    }

    private fun registerObservers() {
        viewModel.getCollections().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    collectionAdapter.submitList(response.data)
                }
                is Response.Error -> {
                    response.message ?: "Unknown error"
                }
            }
        }
    }

    private fun registerEventsListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectionFragmentEvents.collect { event ->
                when (event) {
                    is CollectionViewModel.CollectionFragmentEvents.NavigateToAddCollectionScreen -> {
                        val action =
                            CollectionFragmentDirections.actionGroceryCollectionFragmentToAddEditCollectionFragment(
                                null
                            )
                        findNavController().navigate(action)
                    }
                    is CollectionViewModel.CollectionFragmentEvents.NavigateToEditCollectionScreen -> {
                        val action =
                            CollectionFragmentDirections.actionGroceryCollectionFragmentToAddEditCollectionFragment(
                                event.collection
                            )
                        findNavController().navigate(action)
                    }
                    is CollectionViewModel.CollectionFragmentEvents.NavigateToGroceryListScreen -> {
                        navigateToGroceryListFragment(event.collection)
                    }
                }
            }
        }
    }

    private fun navigateToGroceryListFragment(collection : Collection) {
        val action =
            CollectionFragmentDirections.actionGroceryCollectionFragmentToGroceryListFragment(collection)
        findNavController().navigate(action)
    }

}