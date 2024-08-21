package com.bo.mvvmcleanearchitechturehilt.network

import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

inline fun <reified T : Any> Response<ResponseBody>.convert(): T {
    val body = if (this.isSuccessful) {
        this.body()?.string()
    } else throw HttpException(this)

    return GsonBuilder().serializeNulls().create().fromJson(
        body,
        T::class.java
    )
}

inline fun <reified T : Any> ResponseBody.convert(): T {
    return GsonBuilder().serializeNulls().create().fromJson(
        this.string(),
        T::class.java
    )
}

object ClientSideMessage {
    const val UNEXPECTED_ERROR = "unexpected error!!"
    const val CONNECTION_TIMEOUT = "connection timeout!"
    const val INTERNET_CONNECTION_FAILED = "internet connection failed!"
    const val HOST_NOT_FOUND = "host not found!"
}

object ClientSideErrorCodes {
    const val UNEXPECTED_ERROR = -1
    const val CONNECTION_TIMEOUT = -2
    const val INTERNET_CONNECTION_FAILED = -3
    const val HOST_NOT_FOUND = -4
}
