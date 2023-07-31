package com.example.warriorsofhind.network

sealed class NetworkStatusWrapper<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : NetworkStatusWrapper<T>(data)
    class Loading<T> : NetworkStatusWrapper<T>()
    class Failure<T>(message: String? = null) : NetworkStatusWrapper<T>(message = message)
}