package com.learningis4fun.swifty.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.learningis4fun.swifty.data.Repository

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

}