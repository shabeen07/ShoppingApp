package com.example.shopping_app.views.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app.models.ProductItem
import com.example.shopping_app.respository.local.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    private val _cartItems = MutableLiveData<List<ProductItem>>()
    val cartItems: LiveData<List<ProductItem>> = _cartItems

    init {
        getCartItems()
    }

    private fun getCartItems() {
        viewModelScope.launch {
            cartRepository.getAllItems().observeForever {
                _cartItems.value = it
            }
        }
    }

    fun removeCartItem(itemId: String) {
        viewModelScope.launch {
            cartRepository.delete(itemId)
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            cartRepository.clearAll()
        }
    }

}