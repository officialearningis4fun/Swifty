package com.learningis4fun.swifty.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * id
 * category id
 * name
 * pricePerItem
 * numberOfItems
 * totalPrice
 * weight
 * retailer
 * isPurchased
 */
@Entity(tableName = "groceryListItemTable")
data class GroceryListItemEntity(
    @ColumnInfo(name = "groceryListItemId")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "groceryListItemName")
    val name: String = "",
    @ColumnInfo(name = "groceryListItemCollectionId")
    val collectionId : Int = 0,
    @ColumnInfo(name = "groceryItemCategoryId")
    val categoryId : Int = 0,
    @ColumnInfo(name = "groceryListItemPricePerItem")
    val pricePerItem: Double = 0.0,
    @ColumnInfo(name = "groceryListItemTotalPrice")
    val totalPrice : Double = 0.0,
    @ColumnInfo(name = "groceryListItemWeightPerItem")
    val weightPerItem : Double = 0.0,
    @ColumnInfo(name = "groceryListItemTotalWeight")
    val totalWeight : Double = 0.0,
    @ColumnInfo(name = "groceryListItemWeightUnits")
    val weightUnits : String = "",
    @ColumnInfo(name = "groceryListItemNumberOfItems")
    val numberOfItems : Int = 0,
    @ColumnInfo(name = "groceryListItemRetailerId")
    val retailerId : Int = 0,
    @ColumnInfo(name = "groceryListItemIsPurchased")
    val isPurchased : Boolean = false
)