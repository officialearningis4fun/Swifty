package com.learningis4fun.swifty.ui.groceryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.learningis4fun.swifty.data.Category
import com.learningis4fun.swifty.data.GroceryListItem
import com.learningis4fun.swifty.databinding.GroceryCategoryItemBinding
import com.learningis4fun.swifty.databinding.GroceryListItemBinding
import com.learningis4fun.swifty.util.Mass
import com.learningis4fun.swifty.util.Util

const val header_view_type = 0
const val data_view_type = 1

class GroceryListAdapter(val listener: GroceryListItemClickListener) :
    ListAdapter<Any, RecyclerView.ViewHolder>(
        DIFF_CALLBACK
    ) {

    inner class GroceryListItemViewHolder(private val viewBinding: GroceryListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(groceryItem: GroceryListItem) {
            viewBinding.apply {
                val name = "${groceryItem.name} (${Mass.weightWithUnits(groceryItem.totalWeight)})"
                nameTextView.text = name
                description.text = groceryItem.numberOfItemsText
                priceWeightTextView.text = groceryItem.totalPriceText
            }
        }

        init {
            viewBinding.root.setOnClickListener {
                listener.onGroceryListItemClick(getItem(adapterPosition) as GroceryListItem)
            }
        }
    }

    inner class GroceryCategoryViewHolder(private val viewBinding: GroceryCategoryItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(category: Category) {
            viewBinding.apply {
                categoryTextView.text = category.name
            }
        }
    }

    interface GroceryListItemClickListener {
        fun onGroceryListItemClick(groceryListItem: GroceryListItem)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Category) {
            header_view_type
        } else data_view_type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroceryListItemViewHolder -> {
                holder.bind(getItem(position) as GroceryListItem)
            }

            is GroceryCategoryViewHolder -> {
                holder.bind(getItem(position) as Category)
            }
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewBinding

        return when (viewType) {
            header_view_type -> {
                dataBinding = GroceryCategoryItemBinding.inflate(inflater, parent, false)
                GroceryCategoryViewHolder(dataBinding)
            }
            data_view_type -> {
                dataBinding = GroceryListItemBinding.inflate(inflater, parent, false)
                GroceryListItemViewHolder(dataBinding)
            }
            else -> {
                dataBinding = GroceryListItemBinding.inflate(inflater, parent, false)
                GroceryListItemViewHolder(dataBinding)
            }
        }
    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Any> =
            object : DiffUtil.ItemCallback<Any>() {
                override fun areItemsTheSame(
                    oldItem: Any,
                    newItem: Any
                ): Boolean {
                    return if (oldItem is GroceryListItem && newItem is GroceryListItem) {
                        oldItem.id == newItem.id
                    } else false
                }

                override fun areContentsTheSame(
                    oldItem: Any,
                    newItem: Any
                ): Boolean {
                    return if (oldItem is GroceryListItem && newItem is GroceryListItem) {
                        (oldItem as GroceryListItem).hashCode() == (newItem as GroceryListItem).hashCode()
                    } else false
                }
            }
    }
}