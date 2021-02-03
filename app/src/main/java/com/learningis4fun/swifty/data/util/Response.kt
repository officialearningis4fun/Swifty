package com.learningis4fun.swifty.data.util

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Response<T>(data = data)
    class Error<T>(message: String) : Response<T>(message = message)
}
