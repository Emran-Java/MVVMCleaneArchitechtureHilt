package com.bo.mvvmcleanearchitechturehilt.network.network_data

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()

    data class Error(
        var errorMessage: String,
        var errorData: ErrorData? = null,
        var unauthorized: Boolean = false,
        var errorCode: Int? = null,
    ) : DataState<Nothing>()

    data class Success<T>(var data: T) : DataState<T>()
}