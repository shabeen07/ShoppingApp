package com.example.shopping_app.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    // base url
    private const val BASE_URL = " https://c01a428178544c239bb346a2e3a2293f.api.mockbin.io/"

    // retrofit builder
    private val retrofitBuilder : Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
    }

    // okhttp client
    private val client: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
    }

    // api service
    val apiService: ApiService by lazy {
        retrofitBuilder.build()
            .create(ApiService::class.java)
    }

}