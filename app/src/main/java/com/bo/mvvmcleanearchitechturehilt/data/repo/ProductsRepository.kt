package com.bo.mvvmcleanearchitechturehilt.data.repo

import com.bo.mvvmcleanearchitechturehilt.network.network_data.DataState
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
//    suspend fun getProductsList(): Flow<Result<List<Product>>>
    suspend fun getProductsList(): Flow<DataState<*>>
    suspend fun getProductsList2(): Flow<DataState<*>>
    suspend fun getMedicineName(): Flow<DataState<*>>
}