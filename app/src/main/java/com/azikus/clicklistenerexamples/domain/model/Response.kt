package com.azikus.clicklistenerexamples.domain.model

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure(val throwable: Throwable? = null) : Response<Nothing>()
}
