package com.learningis4fun.swifty.ui.groceryList

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.data.GroceryListItem
import com.learningis4fun.swifty.data.Repository
import com.learningis4fun.swifty.data.SortByOption
import com.learningis4fun.swifty.data.local.util.CategoryEntityMapper
import com.learningis4fun.swifty.data.local.util.GroceryListItemEntityMapper
import com.learningis4fun.swifty.data.local.util.PreferenceManager
import com.learningis4fun.swifty.data.util.SortBy
import com.learningis4fun.swifty.data.util.SortByOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class GroceryListViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val preferenceManager: PreferenceManager,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val collection = savedStateHandle.get<Collection>("collection")

    private val _collectionId = MutableStateFlow(collection?.id ?: -1)
    private var sortByOption = SortBy.HIGHEST_PRICE_FIRST

    private val groceryListItemEntityMapper = GroceryListItemEntityMapper()
    private val categoryEntityMapper = CategoryEntityMapper()

    val toolbarTitle = collection?.name

    private val groceryListFragmentEventsChannel = Channel<GroceryListFragmentEvents>()
    val groceryListFragmentEvents = groceryListFragmentEventsChannel.receiveAsFlow()

    /**
     * Show categories -> boolean
     *
     * Sort by -> higher price first, lower price first,
     * name,
     * TO COME LATER: larger weight first, smallest weight first,
     * many items first, small items first
     *
     * collectionId
     */

    fun groceries(): LiveData<List<Any>> =
        combine(_collectionId, preferenceManager.hideCategories, preferenceManager.sortBy)
        { collectionId, hideCategories, sortBy ->
            Triple(
                collectionId,
                hideCategories,
                sortBy
            )
        }.flatMapLatest { (collectionId, hideCategories, sortBy) ->
            sortByOption = sortBy
            loadCategories(hideCategories, collectionId, sortBy)
        }.asLiveData()

    private fun loadCategories(
        hideCategories: Boolean,
        collectionId: Int,
        sortBy: SortBy
    ): Flow<List<Any>> {

        return if (hideCategories) {
            loadGroceriesWithoutCategories(collectionId, sortBy)
        } else {
            loadCategoriesWithGroceries(collectionId)
        }
    }

    private fun loadCategoriesWithGroceries(collectionId: Int): Flow<List<Any>> {
        var categoriesWithGrocery = flowOf(emptyList<Any>())

        try {
            categoriesWithGrocery = repository.getCategoriesOfCollection(collectionId)
                .flatMapLatest { collectionWithCategories ->
                    repository.getCategoryWithGroceryListItems(
                        collectionWithCategories.categories.map { categoryEntity ->
                            categoryEntity.id
                        }
                    )
                }.mapLatest {
                    val categoryWithGroceryListItems: MutableList<Any> = ArrayList()
                    it.forEach { categoryWithGroceryListItemsEntity ->
                        if (categoryWithGroceryListItemsEntity.groceryListItems.isNotEmpty()) {
                            categoryWithGroceryListItems.add(
                                categoryEntityMapper.mapFromEntity(
                                    categoryWithGroceryListItemsEntity.categoryEntity
                                )
                            )
                            categoryWithGroceryListItems.addAll(
                                groceryListItemEntityMapper.mapFromEntities(
                                    categoryWithGroceryListItemsEntity
                                        .groceryListItems
                                        .filter { groceryListItemEntity ->
                                            groceryListItemEntity.collectionId == collectionId
                                        }
                                )
                            )
                        }
                    }
                    categoryWithGroceryListItems.toList()
                }
        } catch (exception: Exception) {
            Log.e("CategoriesWithGrocery", exception.toString())
        }

        return categoriesWithGrocery
    }

    private fun loadGroceriesWithoutCategories(collectionId: Int, sortBy: SortBy): Flow<List<Any>> {
        return repository.getGroceries(collectionId, sortBy)
    }

    fun hideCategories() = preferenceManager.hideCategories.asLiveData()

    fun sortByOptions() : List<SortByOption> {
        return SortByOptions.getSortByList().map {
            if(it.const == sortByOption.name){
                it.copy(isSelected = true)
            } else it
        }
    }

    fun totalPriceForGroceries() = _collectionId.flatMapLatest { id ->
        var price = flowOf("")
        price
    }.asLiveData()

    fun onGroceryListItemClick(groceryListItem: GroceryListItem?) = viewModelScope.launch {
        groceryListFragmentEventsChannel.send(
            GroceryListFragmentEvents.NavigateToAddEditGroceryListItemScreen(
                groceryListItem,
                _collectionId.value
            )
        )
    }

    fun onAddGroceryListItemClick() = viewModelScope.launch {
        groceryListFragmentEventsChannel.send(
            GroceryListFragmentEvents.NavigateToAddEditGroceryListItemScreen(
                null,
                _collectionId.value
            )
        )
    }

    fun onHideCategoriesClick(hide: Boolean) = viewModelScope.launch {
        preferenceManager.updateHideCategories(hide)
    }

    fun onSortByOptionClick(sortByOption: SortByOption) = viewModelScope.launch {
        preferenceManager.updateSortBy(SortBy.valueOf(sortByOption.const))
    }

    sealed class GroceryListFragmentEvents {
        data class NavigateToAddEditGroceryListItemScreen(
            val groceryListItem: GroceryListItem?,
            val collectionId: Int
        ) : GroceryListFragmentEvents()

        data class SendMessageToScreen(val msg: String) : GroceryListFragmentEvents()
    }

}