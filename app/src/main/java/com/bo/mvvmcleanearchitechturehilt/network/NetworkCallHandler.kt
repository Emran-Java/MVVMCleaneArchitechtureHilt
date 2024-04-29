package com.bo.mvvmcleanearchitechturehilt.network

import com.bo.mvvmcleanearchitechturehilt.network.network_data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object NetworkCallHandler {
    suspend fun <T : Any> handleNetworkCall(callback: suspend () -> T): Flow<DataState<T>> =
        flow {
            emit(DataState.Loading)

            try {
                val networkCall = DataState.Success(callback())

                emit(networkCall)
            } catch (e: Exception) {
                e.printStackTrace()

                emit(e.resolveError())
            }
        }
}
