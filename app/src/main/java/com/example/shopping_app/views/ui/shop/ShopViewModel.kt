package com.example.shopping_app.views.ui.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.models.NetworkResponse
import com.example.shopping_app.models.ProductItem
import com.example.shopping_app.respository.local.CartRepository
import com.example.shopping_app.respository.remote.ShopRepository
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {

    private var _products = MutableLiveData<NetworkResponse<List<ProductItem>>>()
    val products: MutableLiveData<NetworkResponse<List<ProductItem>>>
        get() = _products

    // get products
    fun getProducts() {
        viewModelScope.launch {
           ShopRepository.getProducts()
                .collect {
                    _products.value = it
                }
        }
    }

    fun insertItem(cartRepository: CartRepository,productItem: ProductItem) {
        viewModelScope.launch {
            cartRepository.insert(productItem)
        }
    }
}