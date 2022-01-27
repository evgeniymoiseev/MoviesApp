package com.example.moviesapp.util

sealed class ResponseWrapper<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : ResponseWrapper<T>(data)
    class Error<T>(errorMessage: String, data: T? = null) : ResponseWrapper<T>(data, errorMessage)
    class Loading<T> : ResponseWrapper<T>()
}
