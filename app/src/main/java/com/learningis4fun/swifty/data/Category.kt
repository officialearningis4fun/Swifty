package com.learningis4fun.swifty.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Int = 0,
    val name: String,
    val price : Double = 0.0
) : Parcelable