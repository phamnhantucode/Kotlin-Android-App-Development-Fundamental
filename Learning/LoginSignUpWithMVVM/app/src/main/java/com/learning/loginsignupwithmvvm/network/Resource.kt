package com.learning.loginsignupwithmvvm.network

import okhttp3.ResponseBody
//Wrap success and error of response
sealed class Resource<out T> {
    data class Success<out T>(val value: T): Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ): Resource<Nothing>()
}
