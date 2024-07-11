package com.example.shopping_app.views.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopping_app.respository.local.CartRepository

class CartViewModelFactory(private val cartRepository: CartRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(cartRepository) as T
    }
}