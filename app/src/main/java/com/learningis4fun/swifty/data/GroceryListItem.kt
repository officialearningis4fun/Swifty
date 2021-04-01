package com.learningis4fun.swifty.data

import android.os.Parcelable
import com.learningis4fun.swifty.util.Currency
import com.learningis4fun.swifty.util.Util
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
    val collectionId : Int = -1,
    val categoryId : Int = 0,
    val pricePerItem: Double = 0.0,
    val totalPrice : Double = 0.0,
    val weightPerItem : Double = 0.0,
    val totalWeight : Double = 0.0,
    val weightUnits : String = "",
    val numberOfItems : Int = 1,
    val retailerId : Int = -1,
    val isPurchased : Boolean = false
) : Parcelable{

    val numberOfItemsText = if(numberOfItems == 1) "$numberOfItems item" else "$numberOfItems items"
    val totalPriceText = Currency.monetize(Util.getTotalPrice(pricePerItem, numberOfItems).toString(), "M")
    val weightPerItemText = if(weightPerItem == 0.0) "" else weightPerItem.toString()

}