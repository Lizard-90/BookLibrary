package com.example.data.API

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkModel {

   private val moshi by lazy {
       val moshiBuilder = Moshi.Builder()
           .add(KotlinJsonAdapterFactory())
       moshiBuilder.build()
   }


    private val logginfInterceptor by lazy{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        loggingInterceptor
    }

    private val httpClient by lazy{
        OkHttpClient.Builder()
            .addInterceptor(logginfInterceptor)
            .build()
    }

    private fun getRetrofit(endpointUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endpointUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun createBooksApi(endpointUrl: String): BookApi {
        val retrofit = getRetrofit(endpointUrl)
        return retrofit.create(BookApi::class.java)
    }
}