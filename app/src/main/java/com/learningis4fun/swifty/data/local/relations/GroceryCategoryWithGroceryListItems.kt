package com.learningis4fun.swifty.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.learningis4fun.swifty.data.local.entity.CategoryEntity
import com.learningis4fun.swifty.data.local.entity.GroceryListItemEntity

data class GroceryCategoryWithGroceryListItems(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "groceryItemCategoryId",
        entity = GroceryListItemEntity::class,
        entityColumn = "groceryListItemId"
    )
    val groceryListItems: List<GroceryListItemEntity>
)