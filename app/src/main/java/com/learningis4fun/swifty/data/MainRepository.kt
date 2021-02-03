package com.learningis4fun.swifty.data

import com.learningis4fun.swifty.data.util.Response
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getGroceryCollection() : Flow<Response<List<Collection>>>

}