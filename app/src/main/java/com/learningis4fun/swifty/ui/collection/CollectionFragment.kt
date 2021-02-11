package com.learningis4fun.swifty.ui.collection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.learningis4fun.swifty.R
import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.databinding.FragmentCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CollectionFragment : Fragment(R.layout.fragment_collection),
    CollectionAdapter.GroceryCollectionClickListener {

    val viewModel: CollectionViewModel by viewModels()
    private lateinit var collectionAdapter: CollectionAdapter
    private var binding : FragmentCollectionBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCollectionBinding.bind(view)
        
        collectionAdapter = CollectionAdapter(this)
        registerEventsListener()
        registerObservers()
    }

    override fun onCollectionClick(collection: Collection) {
        viewModel.onCollectionClick(collection)
    }

    private fun registerObservers() {
        viewModel.getCollections().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), printList(it), Toast.LENGTH_LONG).show()
        }
    }

    private fun printList(list: List<Collection>): String {
        var msg: String = ""
        list.forEach {
            msg += it
        }

        return msg
    }

    private fun registerEventsListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.collectionFragmentEvents.collect { event ->
                when (event) {
                    is CollectionViewModel.CollectionFragmentEvents.NavigateToAddScreen -> {
                        Toast.makeText(
                            requireContext(),
                            "Navigate to edit collection Fragment",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}