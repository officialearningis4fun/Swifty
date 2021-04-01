package com.learningis4fun.swifty.data.local.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.learningis4fun.swifty.data.local.entities.CategoryEntity
import com.learningis4fun.swifty.data.local.entities.CollectionEntity
import com.learningis4fun.swifty.data.local.entities.CollectionEntityCategoryEntityCrossRef

data class CategoryWithCollectionsEntity(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "groceryItemCategoryId",
        entityColumn = "groceryCollectionId",
        associateBy = Junction(CollectionEntityCategoryEntityCrossRef::class)
    )
    val collections: List<CollectionEntity>
)