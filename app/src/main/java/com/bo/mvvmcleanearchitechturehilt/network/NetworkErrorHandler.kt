package com.bo.mvvmcleanearchitechturehilt.network

import com.bo.mvvmcleanearchitechturehilt.network.network_data.DataState
import com.bo.mvvmcleanearchitechturehilt.network.network_data.ErrorResponse
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class NetworkErrorExceptions(
    val errorCode: Int? = null,
    val errorMessage: String = ClientSideMessage.UNEXPECTED_ERROR,
    val errorBody: ErrorResponse? = null,
    val unauthorized: Boolean = false
) : Exception() {
    override val message: String?
        get() = errorMessage

    companion object {
        fun parseException(exception: HttpException): NetworkErrorExceptions {
            return try {
                val errorBody =
                    exception.response()?.errorBody()
                        ?.convert() as ErrorResponse

                NetworkErrorExceptions(
                    errorCode = exception.code(),
                    errorMessage = errorBody.messages?.firstOrNull()
                        ?: ClientSideMessage.UNEXPECTED_ERROR,
                    errorBody = errorBody,
                    unauthorized = exception.code() == 401 // unauthorized true if 401
                )
            } catch (ex: Exception) {
                NetworkErrorExceptions(
                    errorCode = exception.code(),
                    errorMessage = ClientSideMessage.UNEXPECTED_ERROR,
                )
            }
        }
    }
}

fun Exception.resolveError(): DataState.Error {
    when (this) {
        is SocketTimeoutException -> {
            val exception =
                NetworkErrorExceptions(
                    errorCode = ClientSideErrorCodes.CONNECTION_TIMEOUT,
                    errorMessage = ClientSideMessage.CONNECTION_TIMEOUT
                )
            return DataState.Error(
                errorCode = exception.errorCode,
                errorMessage = exception.errorMessage,
                unauthorized = exception.unauthorized,
            )
        }

        is ConnectException -> {
            val exception =
                NetworkErrorExceptions(
                    errorCode = ClientSideErrorCodes.INTERNET_CONNECTION_FAILED,
                    errorMessage = ClientSideMessage.INTERNET_CONNECTION_FAILED
                )
            return DataState.Error(
                errorCode = exception.errorCode,
                errorMessage = exception.errorMessage,
                unauthorized = exception.unauthorized,
            )
        }

        is UnknownHostException -> {
            val exception = NetworkErrorExceptions(
                errorCode = ClientSideErrorCodes.HOST_NOT_FOUND,
                errorMessage = ClientSideMessage.HOST_NOT_FOUND
            )
            return DataState.Error(
                errorCode = exception.errorCode,
                errorMessage = exception.errorMessage,
                unauthorized = exception.unauthorized,
            )
        }

        is HttpException -> {
            val exception = NetworkErrorExceptions.parseException(this)
            return DataState.Error(
                errorCode = exception.errorCode,
                errorMessage = exception.errorMessage,
                unauthorized = exception.unauthorized,
                errorData = exception.errorBody?.errorData,
            )
        }
    }

    return DataState.Error(
        errorCode = ClientSideErrorCodes.UNEXPECTED_ERROR,
        errorMessage = ClientSideMessage.UNEXPECTED_ERROR,
        unauthorized = false
    )
}

