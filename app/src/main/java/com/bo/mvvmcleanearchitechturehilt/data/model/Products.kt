package com.bo.mvvmcleanearchitechturehilt.data.model

import com.bo.networkoperationdemo.data.model.Product

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)