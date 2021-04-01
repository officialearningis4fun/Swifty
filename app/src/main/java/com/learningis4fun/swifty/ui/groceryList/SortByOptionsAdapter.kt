package com.learningis4fun.swifty.ui.groceryList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learningis4fun.swifty.R
import com.learningis4fun.swifty.data.SortByOption
import com.learningis4fun.swifty.databinding.ItemMenuSortByBinding

class SortByOptionsAdapter(
    private val listener: SortByOptionClickListener,
    private val context: Context
) :
    ListAdapter<SortByOption, SortByOptionsAdapter.SortByOptionsViewHolder>(
        DIFF_CALLBACK
    ) {

    inner class SortByOptionsViewHolder(private val viewBinding: ItemMenuSortByBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(sortByOption: SortByOption) {
            viewBinding.apply {
                txtSortByName.text = sortByOption.option
                txtSortByName.setTextColor(
                    if (sortByOption.isSelected) context.resources.getColor(R.color.pink_500)
                    else context.resources.getColor(R.color.black)
                )
                imgSortByIcon.visibility =
                    if (sortByOption.isSelected) View.VISIBLE else View.INVISIBLE
            }
        }

        init {
            viewBinding.root.setOnClickListener {
                listener.onSortByOptionClick(getItem(adapterPosition))
            }
        }
    }

    interface SortByOptionClickListener {
        fun onSortByOptionClick(sortByOption: SortByOption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortByOptionsViewHolder {
        val binding =
            ItemMenuSortByBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SortByOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SortByOptionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<SortByOption> =
            object : DiffUtil.ItemCallback<SortByOption>() {
                override fun areItemsTheSame(
                    oldItem: SortByOption,
                    newItem: SortByOption
                ): Boolean {
                    return oldItem.option == newItem.option
                }

                override fun areContentsTheSame(
                    oldItem: SortByOption,
                    newItem: SortByOption
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}