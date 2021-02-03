package com.learningis4fun.swifty.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * id
 * name
 * price
 * weight
 * date
 * numberOfItems
 * isPurchased
 */
@Entity(tableName = "groceryCollectionTable")
data class CollectionEntity(

    @ColumnInfo(name = "groceryCollectionId")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "groceryCollectionName")
    val name: String,
    @ColumnInfo(name = "groceryCollectionPrice")
    val price: Double = 0.0,
    @ColumnInfo(name = "groceryCollectionWeight")
    val weight: Double = 0.0,
    @ColumnInfo(name = "groceryCollectionDate")
    val date: Long = 0L,
    @ColumnInfo(name = "groceryCollectionNumberOfItems")
    val numberOfItems: Int = 0,
    @ColumnInfo(name = "groceryCollectionIsPurchasedProgress")
    val isPurchasedProgress: String = ""
)