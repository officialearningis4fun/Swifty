package com.learningis4fun.swifty.ui.collection

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learningis4fun.swifty.R
import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.databinding.CollectionItemBinding
import com.learningis4fun.swifty.util.Mass
import com.learningis4fun.swifty.util.Util

class CollectionAdapter(val listener: GroceryCollectionClickListener) :
    ListAdapter<Collection, CollectionAdapter.GroceryCollectionViewHolder>(
        DIFF_CALLBACK
    ) {

    inner class GroceryCollectionViewHolder(private val viewBinding: CollectionItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root), PopupMenu.OnMenuItemClickListener {

        fun bind(collection: Collection) {
            viewBinding.apply {
                val details =
                    "${collection.formattedPrice} ∙ ${Mass.weightWithUnits(collection.weight)}"
                nameTextView.text = collection.name
                dateCreated.text = collection.dateString
                priceWeightTextView.text = details
            }
        }

        init {
            viewBinding.root.setOnClickListener {
                listener.onCollectionClick(getItem(adapterPosition))
            }
            viewBinding.moreOptions.setOnClickListener {
                val popup = PopupMenu(it.context, it)
                popup.setOnMenuItemClickListener(this)
                popup.menuInflater.inflate(R.menu.collection_popup_menu, popup.menu)
                popup.show()
            }
        }

        override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
            return when (menuItem?.itemId) {
                R.id.action_popup_menu_delete_collection -> {
                    listener.onDeleteCollectionClick(getItem(adapterPosition))
                    true
                }
                R.id.action_popup_menu_rename_collection -> {
                    listener.onRenameCollectionClick(getItem(adapterPosition))
                    true
                }
                else -> false
            }
        }
    }

    interface GroceryCollectionClickListener {
        fun onCollectionClick(collection: Collection)
        fun onDeleteCollectionClick(collection: Collection)
        fun onRenameCollectionClick(collection: Collection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryCollectionViewHolder {
        val binding =
            CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryCollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroceryCollectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Collection> =
            object : DiffUtil.ItemCallback<Collection>() {
                override fun areItemsTheSame(
                    oldItem: Collection,
                    newItem: Collection
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Collection,
                    newItem: Collection
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
    }
}