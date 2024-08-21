package com.bo.mvvmcleanearchitechturehilt.di

import android.content.Context
import com.bo.mvvmcleanearchitechturehilt.app_data.AppConstant
import com.bo.mvvmcleanearchitechturehilt.network.ProductApi
import com.bo.mvvmcleanearchitechturehilt.data.repo.ProductsRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDiModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun getHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
        /*if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)

            val chuckerInterceptor = ChuckerInterceptor.Builder(context).build()
            builder.addInterceptor(chuckerInterceptor)
        }*/

        val okHttpClient = builder
            .connectTimeout(AppConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConstant.READ_TIMEOUT, TimeUnit.SECONDS)
            .cache(null)
            .addInterceptor { chain ->
                val newRequestBuilder = chain.request().newBuilder()
//                newRequestBuilder.addHeader(
//                    "x-fcm",
//                    providePreference(context).getString(PrefKey.FCM_TOKEN)
//                )
                newRequestBuilder.addHeader("Cache-Control", "no-cache")
                newRequestBuilder.cacheControl(CacheControl.FORCE_NETWORK)
                newRequestBuilder.addHeader("Content-Type", "application/json")

//                if (AppDataController.isRefresh) {
//                    newRequestBuilder.addHeader(
//                        ApiVariables.KEY_AUTHORIZATION,
//                        "Bearer ${providePreference(context).getString(PrefKey.REFRESH_TOKEN)}"
//
//                    )
//                } else {
//                    newRequestBuilder.addHeader(
//                        ApiVariables.KEY_AUTHORIZATION,
//                        "Bearer ${providePreference(context).getString(PrefKey.ACCESS_TOKEN)}"
//                    )
//                }

                chain.proceed(newRequestBuilder.build())
            }
            .build()

        return okHttpClient
    }


    @Provides
    fun provideCommonApiService(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    fun provideProductsRepository(productApi: ProductApi): ProductsRepositoryImpl {
       return ProductsRepositoryImpl(productApi)
    }


}