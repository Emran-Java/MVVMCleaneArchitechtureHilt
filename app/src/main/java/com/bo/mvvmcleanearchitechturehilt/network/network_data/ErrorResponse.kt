package com.bo.mvvmcleanearchitechturehilt.network.network_data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("data") val errorData: ErrorData? = null,
) : RestResponse()

data class ErrorData(
    @SerializedName("blacklisted") val blacklisted: Boolean = false,
    @SerializedName("blacklisted_date") val blacklistedDate: Long? = null,
)
