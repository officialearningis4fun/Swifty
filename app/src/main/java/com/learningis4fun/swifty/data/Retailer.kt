package com.learningis4fun.swifty.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Retailer(
    val id: Int = 0,
    val name: String = "",
    val location: String = ""
) : Parcelable