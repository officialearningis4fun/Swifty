package com.learningis4fun.swifty.data

import androidx.lifecycle.LiveData
import com.learningis4fun.swifty.data.local.entities.CollectionEntity
import com.learningis4fun.swifty.data.local.entities.CollectionEntityCategoryEntityCrossRef
import com.learningis4fun.swifty.data.local.entities.GroceryListItemEntity
import com.learningis4fun.swifty.data.local.relations.CategoryWithGroceryListItemsEntity
import com.learningis4fun.swifty.data.local.relations.CollectionWithCategoriesEntity
import com.learningis4fun.swifty.data.util.Response
import com.learningis4fun.swifty.data.util.SortBy
import kotlinx.coroutines.flow.Flow

interface MainRepository {

     fun getGroceryCollection() : Flow<Response<List<Collection>>>

    suspend fun insertCollectionToDB(collection : Collection)

    suspend fun deleteCollectionFromDB(collection: Collection)

    suspend fun updateCollectionInDB(collection: Collection)

    suspend fun updateCollectionTotalPriceInDBWithId(collectionId: Int, price : Double)

    suspend fun insertCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef)

    suspend fun deleteCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef)

    suspend fun updateCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef)

    suspend fun insertGroceryListItem(groceryListItem: GroceryListItem)

    suspend fun updateGroceryListItem(groceryListItem: GroceryListItem)

    suspend fun deleteGroceryListItem(groceryListItem: GroceryListItem)

    fun getCategoriesOfCollection(collectionId : Int) : Flow<CollectionWithCategoriesEntity>

    suspend fun getCategoriesOfCollectionAsync(collectionId : Int) : CollectionWithCategoriesEntity

    fun getCategoryWithGroceryListItems(categoryIds : List<Int>) : Flow<List<CategoryWithGroceryListItemsEntity>>

    suspend fun getCategoryWithGroceryListItems(categoryId : Int) : CategoryWithGroceryListItemsEntity

    fun getGroceries(collectionId : Int, sortBy: SortBy) : Flow<List<GroceryListItem>>

    suspend fun getGroceriesAsync(collectionId: Int) : List<GroceryListItem>

    suspend fun getAllCategories() : List<Category>

    suspend fun getCollection(collectionId: Int) : CollectionEntity

    fun getTotalPrice(collectionId: Int) : Flow<Double>

    suspend fun getGroceryListItemTotalPrices(collectionId: Int) : List<Double>

    suspend fun getGroceryListItemTotalWeights(collectionId: Int) : List<Double>

    fun getAllCollectionsIds() : Flow<List<Int>>

}