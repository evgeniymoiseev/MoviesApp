package com.example.moviesapp.util

sealed class ErrorType(errorMessage: String) {
    class Internet() : ErrorType("No internet connection")
    class Network() : ErrorType("Something going wrong with network")
    class Conversion() : ErrorType("Conversion error")
    class Limit(errorMessage: String) : ErrorType("Request limit exceed: $errorMessage")
    class Server(errorMessage: String) : ErrorType("Server error: $errorMessage")
}