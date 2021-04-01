package com.learningis4fun.swifty.data.local.db

import androidx.room.*
import com.learningis4fun.swifty.data.local.entities.CollectionEntity
import com.learningis4fun.swifty.data.local.entities.CollectionEntityCategoryEntityCrossRef
import com.learningis4fun.swifty.data.local.relations.CollectionWithCategoriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionCategoryCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef)

    @Update
    suspend fun updateCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef)

    @Delete
    suspend fun deleteCollectionIdAndCategoryId(collectionEntityCategoryEntityCrossRef: CollectionEntityCategoryEntityCrossRef)

    @Transaction
    @Query("SELECT * FROM groceryCollectionTable WHERE groceryCollectionId = :collectionId")
    fun getCategoriesOfCollection(collectionId : Int) : Flow<CollectionWithCategoriesEntity>

    @Transaction
    @Query("SELECT * FROM groceryCollectionTable WHERE groceryCollectionId = :collectionId")
    fun getCategoriesOfCollectionAsync(collectionId : Int) : CollectionWithCategoriesEntity
}