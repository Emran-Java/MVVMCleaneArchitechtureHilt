package com.bo.mvvmcleanearchitechturehilt.network

import com.bo.mvvmcleanearchitechturehilt.data.model.Products
import retrofit2.http.GET

interface ProductApi {

    @GET("products")
    suspend fun getProductsList(): Products

}