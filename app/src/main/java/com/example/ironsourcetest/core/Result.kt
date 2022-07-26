package com.example.ironsourcetest.core

sealed class Result<out T> {
    data class Failure(val error: String) : Result<Nothing>()
    data class Success<out T>(val result: T) : Result<T>()
}