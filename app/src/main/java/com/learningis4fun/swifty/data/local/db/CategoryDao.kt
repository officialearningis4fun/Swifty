package com.learningis4fun.swifty.data.local.db

import androidx.room.*
import com.learningis4fun.swifty.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(categoryEntity: CategoryEntity)

    @Delete
    fun deleteCategory(categoryEntity: CategoryEntity)

    @Update
    fun updateCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM groceryListItemCategoryTable ORDER BY groceryItemCategoryName ASC")
    suspend fun getAllCategories(): List<CategoryEntity>
}