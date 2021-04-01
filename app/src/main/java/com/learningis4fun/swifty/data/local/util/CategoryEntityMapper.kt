package com.learningis4fun.swifty.data.local.util

import com.learningis4fun.swifty.data.Category
import com.learningis4fun.swifty.data.local.entities.CategoryEntity

class CategoryEntityMapper :
    EntityMapper<CategoryEntity, Category> {
    override fun mapFromEntity(entity: CategoryEntity): Category {
        return Category(
            id = entity.id,
            name = entity.name
        )
    }

    override fun mapFromModel(model: Category): CategoryEntity {
        return CategoryEntity(
            id = model.id,
            name = model.name
        )
    }

    fun mapFromEntities(entities: List<CategoryEntity>): List<Category> {
        return entities.map {
            mapFromEntity(it)
        }
    }

    fun mapFromModels(models: List<Category>): List<CategoryEntity> {
        return models.map {
            mapFromModel(it)
        }
    }
}