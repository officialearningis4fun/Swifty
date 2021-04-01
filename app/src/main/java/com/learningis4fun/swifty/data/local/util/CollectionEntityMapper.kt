package com.learningis4fun.swifty.data.local.util

import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.data.local.entities.CollectionEntity

class CollectionEntityMapper : EntityMapper<CollectionEntity, Collection> {
    override fun mapFromEntity(entity: CollectionEntity): Collection {
        return Collection(
            id = entity.id,
            name = entity.name,
            price = entity.price,
            weight = entity.weight,
            date = entity.date,
            numberOfItems = entity.numberOfItems,
            isPurchasedProgress = entity.isPurchasedProgress
        )
    }

    override fun mapFromModel(model: Collection): CollectionEntity {
        return CollectionEntity(
            id = model.id,
            name = model.name,
            price = model.price,
            weight = model.weight,
            date = model.date,
            numberOfItems = model.numberOfItems,
            isPurchasedProgress = model.isPurchasedProgress
        )
    }

    fun mapFromEntities(entities: List<CollectionEntity>): List<Collection> {
        return entities.map {
            mapFromEntity(it)
        }
    }

    fun mapFromModels(models: List<Collection>): List<CollectionEntity> {
        return models.map {
            mapFromModel(it)
        }
    }
}