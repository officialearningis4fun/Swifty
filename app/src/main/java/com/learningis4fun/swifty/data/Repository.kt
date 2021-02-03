package com.learningis4fun.swifty.data

import com.learningis4fun.swifty.data.local.db.GroceryListDao
import com.learningis4fun.swifty.data.util.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val groceryListDao: GroceryListDao) : MainRepository {

    override fun getGroceryCollection(): Flow<Response<List<Collection>>> {
        TODO("Not yet implemented")
    }

}