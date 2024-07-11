package com.example.shopping_app.api

import com.example.shopping_app.models.ProductItem
import retrofit2.http.GET

interface ApiService {

    // get products has no extra params
    @GET(" ")
    suspend fun getProducts(): List<ProductItem>

}