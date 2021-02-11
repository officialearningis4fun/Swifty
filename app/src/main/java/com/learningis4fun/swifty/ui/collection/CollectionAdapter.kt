package com.learningis4fun.swifty.ui.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.databinding.CollectionItemBinding

class CollectionAdapter(val listener: GroceryCollectionClickListener) :
    ListAdapter<Collection, CollectionAdapter.GroceryCollectionViewHolder>(
        DIFF_CALLBACK
    ) {

    inner class GroceryCollectionViewHolder(private val viewBinding: CollectionItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(collection: Collection) {
            viewBinding.apply {
                val details = "${collection.formattedNumberOfItem}, $${collection.formattedPrice} ${collection.formattedWeight}"
                nameTextView.text = collection.name
                dateCreated.text = collection.date.toString()
                priceWeightTextView.text = details
            }
        }

        init {
            viewBinding.root.setOnClickListener {
                listener.onCollectionClick(getItem(adapterPosition))
            }
        }
    }

    interface GroceryCollectionClickListener {
        fun onCollectionClick(collection: Collection)
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