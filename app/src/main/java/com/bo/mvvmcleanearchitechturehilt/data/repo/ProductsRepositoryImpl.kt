package com.bo.mvvmcleanearchitechturehilt.data.repo

import com.bo.mvvmcleanearchitechturehilt.network.ProductApi
import com.bo.mvvmcleanearchitechturehilt.network.network_data.DataState
import com.bo.mvvmcleanearchitechturehilt.network.NetworkCallHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class  ProductsRepositoryImpl@Inject constructor(
    private val productApi: ProductApi
): ProductsRepository {

    override suspend fun getProductsList(): Flow<DataState<*>>{

        return NetworkCallHandler.handleNetworkCall {
            productApi.getProductsList()
        }

       /* return flow {
            emit(Result.Loading())

            val productsFromApi = try {
                api.getProductsList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }

            emit(Result.Success(productsFromApi.products))
        }*/

    }

    override suspend fun getProductsList2(): Flow<DataState<*>>{

        /*return NetworkCallHandler.handleNetworkCall {
            productApi.getProductsList()
        }*/

       return flow {
            emit(DataState.Loading)

            val productsFromApi = try {
                productApi.getProductsList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(DataState.Error(errorMessage = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(DataState.Error(errorMessage = "Error loading products"))
                return@flow
            }  catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(errorMessage = "Error loading products"))
                return@flow
            }

            emit(DataState.Success(productsFromApi))
        }

    }

    override suspend fun getMedicineName(): Flow<DataState<*>>{
        return NetworkCallHandler.handleNetworkCall {
            //productApi.getMedicineName()
        }

    }

}