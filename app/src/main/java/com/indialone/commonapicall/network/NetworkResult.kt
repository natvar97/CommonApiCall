package com.indialone.commonapicall.network

sealed class NetworkResult<T : Any> {
    class Loading<T: Any> : NetworkResult<T>()
    class Success<T : Any>(val data: T?) : NetworkResult<T>()
    class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
}
