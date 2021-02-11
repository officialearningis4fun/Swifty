package com.learningis4fun.swifty.ui.collection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.learningis4fun.swifty.data.Collection
import com.learningis4fun.swifty.data.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CollectionViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val collectionFragmentEventsChannel = Channel<CollectionFragmentEvents>()
    val collectionFragmentEvents = collectionFragmentEventsChannel.receiveAsFlow()

    fun getCollections(): LiveData<List<Collection>> = liveData{
        val list = listOf(
            Collection(id = 0, name = "Monthly grocery"),
            Collection(id = 1, name = "Jan grocery"),
            Collection(id = 2, name = "Feb grocery"),
            Collection(id = 3, name = "Mar grocery")
        )
        emit(list)
    }

    fun onCollectionClick(collection: Collection) = viewModelScope.launch{
        collectionFragmentEventsChannel.send(
            CollectionFragmentEvents.
            NavigateToAddScreen(
                collection,
                "Edit Item"))
    }

    sealed class CollectionFragmentEvents{
        data class NavigateToAddScreen(val collection: Collection, val fragmentTitle : String) : CollectionFragmentEvents()
    }

}