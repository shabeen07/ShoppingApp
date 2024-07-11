package com.example.shopping_app.respository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shopping_app.models.ProductItem

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productItem: ProductItem)

    @Update
    suspend fun update(productItem: ProductItem)

    @Query("SELECT * FROM cart_table ORDER BY itemID DESC")
    fun getAllItems(): LiveData<List<ProductItem>>

    @Query("SELECT * FROM cart_table WHERE itemID = :itemId")
    fun getItem(itemId: String): LiveData<ProductItem>

    @Query("DELETE FROM cart_table WHERE itemId = :itemId")
    suspend fun deleteItem(itemId: String)

    @Query("DELETE FROM cart_table")
    suspend fun clearAll()

    @Query("UPDATE cart_table SET itemQty = :itemQty WHERE itemId = :itemId")
    fun update(itemQty: Int, itemId: String)

}