package com.learningis4fun.swifty.ui.groceryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.learningis4fun.swifty.data.SortByOption
import com.learningis4fun.swifty.databinding.FragmentSortByBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class SortByFragment(private val listener : SortByFragmentListener, private val sortByOptions : List<SortByOption>) : BottomSheetDialogFragment(), SortByOptionsAdapter.SortByOptionClickListener {

    private var binding: FragmentSortByBinding? = null
    private lateinit var sortByOptionsAdapter: SortByOptionsAdapter

    interface SortByFragmentListener{
        fun onSortByClick(sortByOption: SortByOption)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSortByBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sortByOptionsAdapter = SortByOptionsAdapter(this, requireContext())
        sortByOptionsAdapter.submitList(sortByOptions)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.apply {
            recyclerViewSortBy.apply {
                adapter = sortByOptionsAdapter
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSortByOptionClick(sortByOption: SortByOption) {
        listener.onSortByClick(sortByOption)
        dialog?.dismiss()
    }
}