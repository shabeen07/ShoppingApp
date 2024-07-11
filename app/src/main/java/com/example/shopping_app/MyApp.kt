package com.example.shopping_app

import android.app.Application
import com.example.shopping_app.respository.local.CartRepository
import com.example.shopping_app.respository.local.LocalDatabase

class MyApp : Application() {

    private val database by lazy { LocalDatabase.getDatabase(this) }
    val repository by lazy { CartRepository(database.cartDao()) }
}