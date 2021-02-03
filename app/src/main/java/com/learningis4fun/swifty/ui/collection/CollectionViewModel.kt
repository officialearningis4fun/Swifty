package com.learningis4fun.swifty.ui.collection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.learningis4fun.swifty.data.Repository

class CollectionViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

}