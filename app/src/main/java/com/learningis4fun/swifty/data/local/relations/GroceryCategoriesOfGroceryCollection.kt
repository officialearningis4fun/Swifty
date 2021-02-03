package com.learningis4fun.swifty.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.learningis4fun.swifty.data.local.entity.CollectionEntity
import com.learningis4fun.swifty.data.local.entity.CategoryEntity

data class GroceryCategoriesOfGroceryCollection(
    @Embedded val collection: CollectionEntity,
    @Relation(
        parentColumn = "groceryCollectionId",
        entity = CategoryEntity::class,
        entityColumn = "groceryItemCategoryId"
    )
    val groceryCategories: List<CategoryEntity>
)