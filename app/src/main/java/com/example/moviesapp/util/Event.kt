package com.example.moviesapp.util

sealed class Event<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : Event<T>(data)
    class Error<T>(errorMessage: String, data: T? = null) : Event<T>(data, errorMessage)
    class Loading<T> : Event<T>()
}
