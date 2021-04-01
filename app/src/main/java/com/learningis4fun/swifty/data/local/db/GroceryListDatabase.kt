package com.learningis4fun.swifty.data.local.db

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.learningis4fun.swifty.R
import com.learningis4fun.swifty.data.local.entities.*
import com.learningis4fun.swifty.di.ApplicationScope
import com.learningis4fun.swifty.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        CollectionEntity::class,
        GroceryListItemEntity::class,
        RetailerEntity::class,
        CategoryEntity::class,
        CollectionEntityCategoryEntityCrossRef::class],
    exportSchema = false,
    version = 1
)
abstract class GroceryListDatabase : RoomDatabase() {

    abstract fun groceryListDao(): GroceryListDao
    abstract fun groceryCollectionDao(): CollectionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun collectionCategoryCrossRef() : CollectionCategoryCrossRefDao

    class CallBack @Inject constructor(
        private val database: Provider<GroceryListDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().groceryCollectionDao()
            applicationScope.launch {
                dao.insertCollection(
                    CollectionEntity(
                        date = Util.currentDate(),
                        name = "January grocery"
                    )
                )
                dao.insertCollection(
                    CollectionEntity(
                        date = Util.currentDate(),
                        name = "February grocery"
                    )
                )
            }
        }
    }

    class CategoriesCallBack @Inject constructor(
        private val database: Provider<GroceryListDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope,
        private val app: Application
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().categoryDao()
            applicationScope.launch {
                val categories = app.resources.getStringArray(R.array.grocery_categories)
                categories.forEach {
                    dao.insertCategory(CategoryEntity(name = it))
                }
            }
        }
    }

}