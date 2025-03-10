package com.example.shopping_app.respository.local

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.shopping_app.models.ProductItem
import com.example.shopping_app.respository.local.dao.CartDao

class CartRepository(private val cartDao: CartDao) {

    @WorkerThread
    suspend fun insert(productItem: ProductItem) {
        cartDao.insert(productItem)
    }

    @WorkerThread
    suspend fun delete(itemId: String) {
        cartDao.deleteItem(itemId)
    }

    @WorkerThread
    suspend fun clearAll() {
        cartDao.clearAll()
    }

    fun getAllItems(): LiveData<List<ProductItem>> {
        return cartDao.getAllItems()
    }

}