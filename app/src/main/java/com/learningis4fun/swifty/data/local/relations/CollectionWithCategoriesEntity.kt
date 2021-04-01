package com.learningis4fun.swifty.data.local.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.learningis4fun.swifty.data.local.entities.CategoryEntity
import com.learningis4fun.swifty.data.local.entities.CollectionEntity
import com.learningis4fun.swifty.data.local.entities.CollectionEntityCategoryEntityCrossRef

data class CollectionWithCategoriesEntity(
    @Embedded
    val collection: CollectionEntity,
    @Relation(
        parentColumn = "groceryCollectionId",
        entityColumn = "groceryItemCategoryId",
        associateBy = Junction(CollectionEntityCategoryEntityCrossRef::class)
    )
    val categories: List<CategoryEntity>
)