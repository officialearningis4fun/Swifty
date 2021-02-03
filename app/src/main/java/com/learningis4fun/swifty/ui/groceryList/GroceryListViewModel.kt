package com.learningis4fun.swifty.ui.groceryList

import androidx.lifecycle.ViewModel
import com.learningis4fun.swifty.data.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GroceryListViewModel(
    private val repository: Repository
) : ViewModel() {

}