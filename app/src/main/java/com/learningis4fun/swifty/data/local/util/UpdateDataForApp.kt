package com.learningis4fun.swifty.data.local.util

import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.data.Repository
import com.learningis4fun.swifty.data.local.entities.CollectionEntityCategoryEntityCrossRef
import com.learningis4fun.swifty.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateDataForApp @Inject constructor(
    private val repository: Repository,
    @ApplicationScope private val scope: CoroutineScope
) {
    private val collectionEntityMapper = CollectionEntityMapper()

    fun updateCollectionTotalPriceAndTotalWeight(collectionId: Int) = scope.launch {
        val groceryListItemTotalPrices = repository.getGroceryListItemTotalPrices(collectionId)

        val groceryListItemTotalWeights = repository.getGroceryListItemTotalWeights(collectionId)

        val collectionTotalPrice = withContext(Dispatchers.Default) {
            groceryListItemTotalPrices.sum()
        }

        val collectionTotalWeight = withContext(Dispatchers.Default){
            groceryListItemTotalWeights.sum()
        }

        val collection = collectionEntityMapper.mapFromEntity(
            repository.getCollection(collectionId).copy(price = collectionTotalPrice, weight = collectionTotalWeight)
        )

        repository.updateCollectionInDB(collection)
    }

    /**
     * Delete Collection, GroceryListItems associated with it and also the CollectionCategoryCrossRef
     */
    fun deleteCollection(collection: Collection) = scope.launch {
        val collectionId = collection.id

        val groceryList = repository.getGroceriesAsync(collectionId)
        val categories = repository.getCategoriesOfCollectionAsync(collectionId).categories

        // delete the collection

        // delete the groceryListItems
        groceryList.forEach { groceryListItem ->
            repository.deleteGroceryListItem(groceryListItem)
        }

        // delete the collectionCategoryCrossRefs
        categories.forEach { category ->
            repository.deleteCollectionIdAndCategoryId(
                CollectionEntityCategoryEntityCrossRef(
                    collection.id,
                    category.id
                )
            )
        }

        repository.deleteCollectionFromDB(collection)
    }

    /**
     * When the category for groceryListItem is changed, delete the collectionCategory if such a relation has no groceryListItems
     */
    fun updateGroceryListItemCategory(collectionId: Int, previousCategoryId: Int) = scope.launch {
        // categoryId of previous category
        val categoryWithGroceryListItems = repository.getCategoryWithGroceryListItems(previousCategoryId)

        if (categoryWithGroceryListItems.groceryListItems.isEmpty()) {
            // delete the collectionCategoryEntity
            repository.deleteCollectionIdAndCategoryId(
                CollectionEntityCategoryEntityCrossRef(
                    collectionId,
                    previousCategoryId
                )
            )
        }
    }
}