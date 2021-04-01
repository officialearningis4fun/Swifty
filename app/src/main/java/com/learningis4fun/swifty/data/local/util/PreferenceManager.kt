package com.learningis4fun.swifty.data.local.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.learningis4fun.swifty.data.util.SortBy
import com.learningis4fun.swifty.ui.groceryList.GroceryListViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore(name = "swiftyPreferences")

    val hideCategories = dataStore.data.map {
        it[PreferenceKeys.HIDE_CATEGORIES] ?: false
    }

    @ExperimentalCoroutinesApi
    val sortBy = dataStore.data.map {
        val sortBy = it[PreferenceKeys.SORT_BY]
        if(sortBy == null){
            SortBy.HIGHEST_PRICE_FIRST
        } else {
            SortBy.valueOf(sortBy)
        }

    }

    suspend fun updateHideCategories(hide : Boolean){
        dataStore.edit {
            it[PreferenceKeys.HIDE_CATEGORIES] = hide
        }
    }

    suspend fun updateSortBy(sortBy: SortBy){
        dataStore.edit {
            it[PreferenceKeys.SORT_BY] = sortBy.name
        }
    }

    object PreferenceKeys {

        val HIDE_CATEGORIES = booleanPreferencesKey("hide categories")

        val SORT_BY = stringPreferencesKey("sort by")
    }

}