package com.learningis4fun.swifty.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "groceryCollectionGroceryListItemCrossRef", primaryKeys = ["groceryCollectionId", "groceryItemCategoryId"])
data class CollectionEntityCategoryEntityCrossRef(

    @ColumnInfo(name = "groceryCollectionId")
    val groceryCollectionEntityId: Int,
    @ColumnInfo(name = "groceryItemCategoryId")
    val groceryListItemCategoryId: Int
)