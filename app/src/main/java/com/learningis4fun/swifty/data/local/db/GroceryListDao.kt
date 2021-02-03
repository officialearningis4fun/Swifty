package com.learningis4fun.swifty.data.local.db

import androidx.room.*
import com.learningis4fun.swifty.data.local.entity.CollectionEntity
import com.learningis4fun.swifty.data.local.entity.CategoryEntity
import com.learningis4fun.swifty.data.local.entity.GroceryListItemEntity
import com.learningis4fun.swifty.data.local.entity.RetailerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryListDao {

    @Insert
    suspend fun insertGroceryCollection(collectionEntity: CollectionEntity)

    @Insert
    suspend fun insertGroceryListItem(groceryListItemEntity: GroceryListItemEntity)

    @Insert
    suspend fun insertGroceryListItemCategory(categoryEntity: CategoryEntity)

    @Insert
    suspend fun insertRetailer(retailerEntity: RetailerEntity)

    @Update
    suspend fun updateGroceryCollection(collectionEntity: CollectionEntity)

    @Update
    suspend fun updateGroceryListItem(groceryListItemEntity: GroceryListItemEntity)

    @Update
    suspend fun updateGroceryListItemCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateRetailer(retailerEntity: RetailerEntity)

    @Delete
    suspend fun deleteGroceryCollection(collectionEntity: CollectionEntity)

    @Delete
    suspend fun deleteGroceryListItem(groceryListItemEntity: GroceryListItemEntity)

    @Delete
    suspend fun deleteGroceryListItemCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun DeleteRetailer(retailerEntity: RetailerEntity)

}