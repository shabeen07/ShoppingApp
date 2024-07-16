package com.example.shopping_app.respository.remote

import com.example.shopping_app.api.ApiClient
import com.example.shopping_app.models.NetworkResponse
import com.example.shopping_app.models.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object ShopRepository {

    fun getProducts() : Flow<NetworkResponse<List<ProductItem>>>  = flow {
        // emit loading
        emit(NetworkResponse.Loading())
        val response = try {
            ApiClient.apiService.getProducts()
        }catch (e: Exception){
            // emit error
            emit(NetworkResponse.Error(e.localizedMessage ?: "Unknown Error"))
            return@flow
        }
        // emit success if response is successful
        emit(NetworkResponse.Success(response))
    }.flowOn(Dispatchers.IO)

}