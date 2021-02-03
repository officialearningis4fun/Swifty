package com.learningis4fun.swifty.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * id
 * name
 * collection id
 * category id
 * pricePerItem
 * numberOfItems
 * totalPrice
 * weight
 * retailer
 * isPurchased
 */

@Parcelize
data class GroceryListItem(
    val id: Int = 0,
    val name: String = "",
    val collectionId : Int = 0,
    val categoryId : Int = 0,
    val pricePerItem: Double = 0.0,
    val totalPrice : Double = 0.0,
    val weight : Double = 0.0,
    val numberOfItems : Int = 0,
    val retailerId : Int = 0,
    val isPurchased : Boolean = false
) : Parcelable