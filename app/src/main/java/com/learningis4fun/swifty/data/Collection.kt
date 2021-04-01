package com.learningis4fun.swifty.data

import android.os.Parcelable
import com.learningis4fun.swifty.util.Currency
import com.learningis4fun.swifty.util.Util
import kotlinx.android.parcel.Parcelize

/**
 * id
 * name
 * price
 * weight
 * date
 * numberOfItems
 * isPurchased
 */

@Parcelize
data class Collection(
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val weight: Double = 0.0,
    val date: Long = 0L,
    val numberOfItems: Int = 0,
    val isPurchasedProgress: String = ""
) : Parcelable {

    val formattedPrice
        get() = Currency.monetize(price.toString(), "M")

    val formattedWeight
        get() = if (weight == 0.0) "${0} kg" else "$weight kg"

    val formattedNumberOfItem
        get() = if (numberOfItems == 1) "$numberOfItems item" else "$numberOfItems items"

    val dateString
    get() = Util.dateFromLong(date)
}