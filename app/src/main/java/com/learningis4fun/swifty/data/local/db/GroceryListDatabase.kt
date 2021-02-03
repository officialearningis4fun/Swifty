package com.learningis4fun.swifty.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.learningis4fun.swifty.data.local.entity.CollectionEntity
import com.learningis4fun.swifty.data.local.entity.CategoryEntity
import com.learningis4fun.swifty.data.local.entity.GroceryListItemEntity
import com.learningis4fun.swifty.data.local.entity.RetailerEntity
import com.learningis4fun.swifty.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [CollectionEntity::class, GroceryListItemEntity::class, RetailerEntity::class, CategoryEntity::class],
    exportSchema = false,
    version = 1
)
abstract class GroceryListDatabase : RoomDatabase() {

    abstract fun groceryListDao(): GroceryListDao

    class CallBack @Inject constructor(
        private val database: Provider<GroceryListDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().groceryListDao()
            applicationScope.launch {
                dao.insertGroceryCollection(CollectionEntity(name = "January list"))
                dao.insertGroceryCollection(CollectionEntity(name = "February list"))
            }
        }
    }

}