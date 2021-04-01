package com.learningis4fun.swifty.ui.collection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.data.Repository
import com.learningis4fun.swifty.data.local.util.UpdateDataForApp
import com.learningis4fun.swifty.data.util.Response
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CollectionViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val updateDataForApp: UpdateDataForApp
) : ViewModel() {

    private val collectionFragmentEventsChannel = Channel<CollectionFragmentEvents>()
    val collectionFragmentEvents = collectionFragmentEventsChannel.receiveAsFlow()

    fun getCollections(): LiveData<Response<List<Collection>>> = liveData {
        emitSource(repository.getGroceryCollection().asLiveData())
    }

    fun onCollectionClick(collection: Collection) = viewModelScope.launch {
        collectionFragmentEventsChannel.send(CollectionFragmentEvents.NavigateToGroceryListScreen(collection))
    }

    fun onAddCollectionClick() = viewModelScope.launch {
        collectionFragmentEventsChannel.send(
            CollectionFragmentEvents.NavigateToAddCollectionScreen("Add Item")
        )
    }

    fun onDeleteCollection(collection: Collection) = viewModelScope.launch {
       updateDataForApp.deleteCollection(collection)
    }

    fun onRenameCollection(collection: Collection) = viewModelScope.launch {
        collectionFragmentEventsChannel.send(
            CollectionFragmentEvents.NavigateToEditCollectionScreen(collection, "Edit Item")
        )
    }


    sealed class CollectionFragmentEvents {
        data class NavigateToEditCollectionScreen(
            val collection: Collection?, val fragmentTitle: String
        ) : CollectionFragmentEvents()

        data class NavigateToAddCollectionScreen(val fragmentTitle: String) :
            CollectionFragmentEvents()

        data class NavigateToGroceryListScreen(val collection: Collection) :
            CollectionFragmentEvents()
    }

}