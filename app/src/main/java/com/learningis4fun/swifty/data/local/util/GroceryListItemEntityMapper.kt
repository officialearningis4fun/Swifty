package com.learningis4fun.swifty.data.local.util

import com.learningis4fun.swifty.data.GroceryListItem
import com.learningis4fun.swifty.data.local.entities.GroceryListItemEntity

class GroceryListItemEntityMapper : EntityMapper<GroceryListItemEntity, GroceryListItem> {
    override fun mapFromEntity(entity: GroceryListItemEntity): GroceryListItem {
        return GroceryListItem(
            id = entity.id,
            name = entity.name,
            collectionId = entity.collectionId,
            categoryId = entity.categoryId,
            pricePerItem = entity.pricePerItem,
            totalPrice = entity.totalPrice,
            weightPerItem = entity.weightPerItem,
            totalWeight = entity.totalWeight,
            weightUnits = entity.weightUnits,
            numberOfItems = entity.numberOfItems,
            retailerId = entity.retailerId,
            isPurchased = entity.isPurchased
        )
    }

    override fun mapFromModel(model: GroceryListItem): GroceryListItemEntity {
        return GroceryListItemEntity(
            id = model.id,
            name = model.name,
            collectionId = model.collectionId,
            categoryId = model.categoryId,
            pricePerItem = model.pricePerItem,
            totalPrice = model.totalPrice,
            weightPerItem = model.weightPerItem,
            totalWeight = model.totalWeight,
            weightUnits = model.weightUnits,
            numberOfItems = model.numberOfItems,
            retailerId = model.retailerId,
            isPurchased = model.isPurchased
        )
    }

    fun mapFromEntities(entities: List<GroceryListItemEntity>): List<GroceryListItem> {
        return entities.map {
            mapFromEntity(it)
        }
    }

    fun mapFromModels(models: List<GroceryListItem>): List<GroceryListItemEntity> {
        return models.map {
            mapFromModel(it)
        }
    }
}