package com.learningis4fun.swifty.di

import android.app.Application
import androidx.room.Room
import com.learningis4fun.swifty.data.local.db.GroceryListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application,
        callBack: GroceryListDatabase.CallBack,
        categoryCallBack : GroceryListDatabase.CategoriesCallBack
    ) =
        Room.databaseBuilder(app, GroceryListDatabase::class.java, "groceryListDatabase.db")
            .fallbackToDestructiveMigration()
            .addCallback(callBack)
            .addCallback(categoryCallBack)
            .build()

    @Singleton
    @Provides
    fun provideGroceryListDao(db: GroceryListDatabase) = db.groceryListDao()

    @Singleton
    @Provides
    fun provideGroceryCollectionDao(db: GroceryListDatabase) = db.groceryCollectionDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: GroceryListDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideCollectionCategoryCrossRefDao(db: GroceryListDatabase) = db.collectionCategoryCrossRef()

    @ApplicationScope
    @Provides
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope