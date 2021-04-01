package com.learningis4fun.swifty.data.local.db

import androidx.room.*
import com.learningis4fun.swifty.data.local.entities.CollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Insert
    suspend fun insertCollection(collectionEntity: CollectionEntity)

    @Update
    suspend fun updateCollection(collectionEntity: CollectionEntity)

    @Delete
    suspend fun deleteCollection(collectionEntity: CollectionEntity)

    @Query("SELECT * FROM groceryCollectionTable WHERE groceryCollectionId =:collectionId")
    fun getCollection(collectionId : Int) : CollectionEntity

    @Query("SELECT * FROM groceryCollectionTable ORDER BY groceryCollectionDate ASC")
    fun getAllCollections() : Flow<List<CollectionEntity>>

    @Query("SELECT groceryCollectionId FROM groceryCollectionTable")
    fun getAllCollectionsIds() : Flow<List<Int>>
}