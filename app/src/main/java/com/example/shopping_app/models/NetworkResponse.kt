package com.example.shopping_app.models

// generic class for network request
sealed class NetworkResponse<T> {
    class Success<T>(val data: T) : NetworkResponse<T>()
    class Error<T>(val message: String) : NetworkResponse<T>()
    class Loading<T> : NetworkResponse<T>()
}