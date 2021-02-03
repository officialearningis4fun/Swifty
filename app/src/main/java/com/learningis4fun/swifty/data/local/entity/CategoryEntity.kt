package com.learningis4fun.swifty.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groceryListItemCategoryTable")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "groceryItemCategoryId")
    val id: Int = 0,
    @ColumnInfo(name = "groceryItemCategoryName")
    val name: String
)