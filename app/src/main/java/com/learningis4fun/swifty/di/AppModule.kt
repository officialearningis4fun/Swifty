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
        callBack: GroceryListDatabase.CallBack
    ) =
        Room.databaseBuilder(app, GroceryListDatabase::class.java, "groceryListDatabase.db")
            .fallbackToDestructiveMigration()
            .addCallback(callBack)
            .build()

    @Provides
    fun provideGroceryListDao(db: GroceryListDatabase) = db.groceryListDao()

    @ApplicationScope
    @Provides
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope