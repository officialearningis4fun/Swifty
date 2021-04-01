package com.learningis4fun.swifty.data

import com.learningis4fun.swifty.data.local.db.CategoryDao
import com.learningis4fun.swifty.data.local.db.CollectionCategoryCrossRefDao
import com.learningis4fun.swifty.data.local.db.CollectionDao
import com.learningis4fun.swifty.data.local.db.GroceryListDao
import com.learningis4fun.swifty.data.local.entities.CollectionEntity
import com.learningis4fun.swifty.data.local.entities.CollectionEntityCategoryEntityCrossRef
import com.learningis4fun.swifty.data.local.relations.CategoryWithGroceryListItemsEntity
import com.learningis4fun.swifty.data.local.relations.CollectionWithCategoriesEntity
import com.learningis4fun.swifty.data.local.util.CategoryEntityMapper
import com.learningis4fun.swifty.data.local.util.CollectionEntityMapper
import com.learningis4fun.swifty.data.local.util.GroceryListItemEntityMapper
import com.learningis4fun.swifty.data.util.Response
import com.learningis4fun.swifty.data.util.SortBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val groceryListDao: GroceryListDao,
    private val collectionDao: CollectionDao,
    private val categoryDao: CategoryDao,
    private val collectionCategoryCrossRefDao: CollectionCategoryCrossRefDao
) : MainRepository {

    private val collectionEntityMapper = CollectionEntityMapper()
    private val categoryEntityMapper = CategoryEntityMapper()
    private val groceryListEntityMapper = GroceryListItemEntityMapper()

    override suspend fun getCollection(collectionId: Int): CollectionEntity {
        return collectionDao.getCollection(collectionId)
    }

    override fun getGroceryCollection(): Flow<Response<List<Collection>>> {
        return try {
            collectionDao.getAllCollections().map {
                Response.Success(collectionEntityMapper.mapFromEntities(it))
            }
        } catch (exception: Exception) {
            flowOf(Response.Error(exception.message ?: "Unknown error"))
        }
    }

    override suspend fun insertCollectionToDB(collection: Collection) {
        collectionDao.insertCollection(collectionEntityMapper.mapFromModel(collection))
    }

    override suspend fun deleteCollectionFromDB(collection: Collection) {
        collectionDao.deleteCollection(collectionEntityMapper.mapFromModel(collection))
    }

    override suspend fun updateCollectionInDB(collection: Collection) {
        collectionDao.updateCollection(collectionEntityMapper.mapFromModel(collection))
    }

    override suspend fun updateCollectionTotalPriceInDBWithId(collectionId: Int, price: Double) {
        val collection = withContext(Dispatchers.IO) {
            collectionDao.getCollection(collectionId).copy(price = price)
        }
        collectionDao.updateCollection(collection)
    }

    override suspend fun insertCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef) {
        collectionCategoryCrossRefDao.insertCollectionIdAndCategoryId(
            collectionEntityCategoryEntityCrossRef
        )
    }

    override suspend fun deleteCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef) {
        collectionCategoryCrossRefDao.deleteCollectionIdAndCategoryId(
            collectionEntityCategoryEntityCrossRef
        )
    }

    override suspend fun updateCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef) {
        collectionCategoryCrossRefDao.updateCollectionIdAndCategoryId(
            collectionEntityCategoryEntityCrossRef
        )
    }

    override suspend fun insertGroceryListItem(groceryListItem: GroceryListItem) {
        groceryListDao.insertGroceryListItem(groceryListEntityMapper.mapFromModel(groceryListItem))
    }

    override suspend fun updateGroceryListItem(groceryListItem: GroceryListItem) {
        groceryListDao.updateGroceryListItem(groceryListEntityMapper.mapFromModel(groceryListItem))
    }

    override suspend fun deleteGroceryListItem(groceryListItem: GroceryListItem) {
        groceryListDao.deleteGroceryListItem(groceryListEntityMapper.mapFromModel(groceryListItem))
    }

    override fun getCategoriesOfCollection(collectionId: Int): Flow<CollectionWithCategoriesEntity> {
        return collectionCategoryCrossRefDao.getCategoriesOfCollection(collectionId)
    }

    override suspend fun getCategoriesOfCollectionAsync(collectionId: Int): CollectionWithCategoriesEntity {
        return collectionCategoryCrossRefDao.getCategoriesOfCollectionAsync(collectionId)
    }

    override fun getCategoryWithGroceryListItems(categoryIds: List<Int>): Flow<List<CategoryWithGroceryListItemsEntity>> {
        return groceryListDao.getCategoriesWithGroceries(categoryIds)
    }

    override suspend fun getCategoryWithGroceryListItems(categoryId: Int): CategoryWithGroceryListItemsEntity {
        return groceryListDao.getCategoryWithGroceriesAsync(categoryId)
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories().map {
            categoryEntityMapper.mapFromEntity(it)
        }
    }

    @ExperimentalCoroutinesApi
    override fun getGroceries(collectionId: Int, sortBy: SortBy): Flow<List<GroceryListItem>> {
        return groceryListDao.getGroceries(collectionId, sortBy).map {
            groceryListEntityMapper.mapFromEntities(it)
        }
    }

    override suspend fun getGroceriesAsync(collectionId: Int): List<GroceryListItem> {
        return groceryListEntityMapper.mapFromEntities(
            groceryListDao.getGroceriesAsync(collectionId)
        )
    }

    override fun getTotalPrice(collectionId: Int): Flow<Double> {
        return groceryListDao.getTotalPrice(collectionId)
    }

    override suspend fun getGroceryListItemTotalPrices(collectionId: Int): List<Double> {
        return groceryListDao.getGroceryListItemTotalPrices(collectionId)
    }

    override suspend fun getGroceryListItemTotalWeights(collectionId: Int): List<Double> {
        return groceryListDao.getGroceryListItemTotalWeights(collectionId)
    }


    override fun getAllCollectionsIds(): Flow<List<Int>> {
        return collectionDao.getAllCollectionsIds()
    }
}