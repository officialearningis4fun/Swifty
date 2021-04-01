package com.learningis4fun.swifty.data.local.db

import androidx.room.*
import com.learningis4fun.swifty.data.local.entities.CategoryEntity
import com.learningis4fun.swifty.data.local.entities.GroceryListItemEntity
import com.learningis4fun.swifty.data.local.entities.RetailerEntity
import com.learningis4fun.swifty.data.local.relations.CategoryWithGroceryListItemsEntity
import com.learningis4fun.swifty.data.util.SortBy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryListDao {

    @Insert
    suspend fun insertGroceryListItem(groceryListItemEntity: GroceryListItemEntity)

    @Insert
    suspend fun insertGroceryListItemCategory(categoryEntity: CategoryEntity)

    @Insert
    suspend fun insertRetailer(retailerEntity: RetailerEntity)

    @Update
    suspend fun updateGroceryListItem(groceryListItemEntity: GroceryListItemEntity)

    @Update
    suspend fun updateGroceryListItemCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateRetailer(retailerEntity: RetailerEntity)

    @Delete
    suspend fun deleteGroceryListItem(groceryListItemEntity: GroceryListItemEntity)

    @Delete
    suspend fun deleteGroceryListItemCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteRetailer(retailerEntity: RetailerEntity)

    @Transaction
    @Query("SELECT * FROM groceryListItemCategoryTable WHERE groceryItemCategoryId IN (:categoryIds)")
    fun getCategoriesWithGroceries(categoryIds: List<Int>): Flow<List<CategoryWithGroceryListItemsEntity>>

    @Transaction
    @Query("SELECT * FROM groceryListItemCategoryTable WHERE groceryItemCategoryId =:categoryId")
    suspend fun getCategoryWithGroceriesAsync(categoryId: Int): CategoryWithGroceryListItemsEntity

    /**
     * GetGroceries, order by higher price first
     */
    @Query("SELECT * FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId ORDER BY groceryListItemTotalPrice DESC")
    fun getGroceriesSortByHighestPriceFirst(collectionId: Int): Flow<List<GroceryListItemEntity>>

    /**
     * GetGroceries, order by lower price first
     */
    @Query("SELECT * FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId ORDER BY groceryListItemTotalPrice ASC")
    fun getGroceriesSortByLowestPriceFirst(collectionId: Int): Flow<List<GroceryListItemEntity>>

    /**
     * GetGroceries, order by small items first
     */
    @Query("SELECT * FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId ORDER BY groceryListItemNumberOfItems ASC")
    fun getGroceriesSortBySmallestItemsFirst(collectionId: Int): Flow<List<GroceryListItemEntity>>

    /**
     * GetGroceries, order by many items first
     */
    @Query("SELECT * FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId ORDER BY groceryListItemNumberOfItems DESC")
    fun getGroceriesSortByManyItemsFirst(collectionId: Int): Flow<List<GroceryListItemEntity>>

    /**
     * GetGroceries, order by lowest weight first
     */
    @Query("SELECT * FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId ORDER BY groceryListItemTotalWeight ASC")
    fun getGroceriesSortByLowestWeightFirst(collectionId: Int): Flow<List<GroceryListItemEntity>>

    /**
     * GetGroceries, order by highest weight first
     */
    @Query("SELECT * FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId ORDER BY groceryListItemTotalWeight DESC")
    fun getGroceriesSortByHighestWeightFirst(collectionId: Int): Flow<List<GroceryListItemEntity>>

    @ExperimentalCoroutinesApi
    fun getGroceries(collectionId: Int, sortBy: SortBy): Flow<List<GroceryListItemEntity>> {
        return when (sortBy) {
            SortBy.SMALLEST_PRICE_FIRST -> getGroceriesSortByLowestPriceFirst(collectionId)
            SortBy.HIGHEST_PRICE_FIRST -> getGroceriesSortByHighestPriceFirst(collectionId)
            SortBy.SMALLEST_WEIGHT_FIRST -> getGroceriesSortByLowestWeightFirst(collectionId)
            SortBy.HIGHEST_WEIGHT_FIRST -> getGroceriesSortByHighestWeightFirst(collectionId)
            SortBy.MOST_ITEMS_FIRST -> getGroceriesSortByManyItemsFirst(collectionId)
            SortBy.LEAST_ITEMS_FIRST -> getGroceriesSortBySmallestItemsFirst(collectionId)
        }
    }

    @Query("SELECT * FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId")
    suspend fun getGroceriesAsync(collectionId: Int): List<GroceryListItemEntity>

    @Query("SELECT * FROM groceryListItemCategoryTable")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT SUM(groceryListItemTotalPrice) FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId")
    fun getTotalPrice(collectionId: Int): Flow<Double>

    @Query("SELECT groceryListItemTotalPrice FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId")
    suspend fun getGroceryListItemTotalPrices(collectionId: Int): List<Double>

    @Query("SELECT groceryListItemTotalWeight FROM groceryListItemTable WHERE groceryListItemCollectionId = :collectionId")
    suspend fun getGroceryListItemTotalWeights(collectionId: Int) : List<Double>

}