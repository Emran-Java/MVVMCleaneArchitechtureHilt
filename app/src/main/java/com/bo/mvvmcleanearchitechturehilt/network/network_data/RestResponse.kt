package com.bo.mvvmcleanearchitechturehilt.network.network_data

import com.google.gson.annotations.SerializedName

open class RestResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("messages") val messages: List<String>? = null,
)