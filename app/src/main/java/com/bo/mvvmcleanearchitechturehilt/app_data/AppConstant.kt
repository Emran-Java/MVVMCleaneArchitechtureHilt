package com.bo.mvvmcleanearchitechturehilt.app_data

class AppConstant {

    companion object{
        var READ_TIMEOUT: Long = 120
        var CONNECTION_TIMEOUT: Long = 120
        const val BASE_URL = "https://dummyjson.com/"//BuildConfig.SERVER_URL
//        const val BASE_URL = "https://script.google.com/macros/s/"
    }
}