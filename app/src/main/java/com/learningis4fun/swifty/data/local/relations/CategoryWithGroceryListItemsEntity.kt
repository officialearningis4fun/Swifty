package com.learningis4fun.swifty.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.learningis4fun.swifty.data.local.entities.CategoryEntity
import com.learningis4fun.swifty.data.local.entities.GroceryListItemEntity

data class CategoryWithGroceryListItemsEntity(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "groceryItemCategoryId",
        entityColumn = "groceryItemCategoryId"
    )
    val groceryListItems: List<GroceryListItemEntity>
)