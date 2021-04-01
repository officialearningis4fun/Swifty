package com.learningis4fun.swifty.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "groceryCollectionGroceryListItemCrossRef",
    primaryKeys = ["groceryCollectionId", "groceryItemCategoryId"],
indices = [Index("groceryItemCategoryId")]
)
data class CollectionEntityCategoryEntityCrossRef(

    @ColumnInfo(name = "groceryCollectionId")
    val groceryCollectionEntityId: Int,
    @ColumnInfo(name = "groceryItemCategoryId")
    val groceryListItemCategoryId: Int

)