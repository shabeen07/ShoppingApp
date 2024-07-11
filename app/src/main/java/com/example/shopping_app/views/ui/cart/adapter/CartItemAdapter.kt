package com.example.shopping_app.views.ui.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_app.databinding.CartItemRowBinding
import com.example.shopping_app.databinding.ShopItemRowBinding
import com.example.shopping_app.models.ProductItem

class CartItemAdapter(
    private val context: Context, private var productList: List<ProductItem>,
    private val cartClickCallback: CartClickCallback
) :
    RecyclerView.Adapter<CartItemAdapter.MyHolder>() {

    class MyHolder(val cartItemRowBinding: CartItemRowBinding) : RecyclerView.ViewHolder(
        cartItemRowBinding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = CartItemRowBinding.inflate(layoutInflater, parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val productItem = productList[position]
        holder.cartItemRowBinding.itemName.text = productItem.itemName
        val price = "$"+ productItem.sellingPrice
        holder.cartItemRowBinding.price.text = price
        val tax = "Total: $"+ productItem.getTotalAmount().toString()
        holder.cartItemRowBinding.tvTotal.text = tax

        // set quantity
        holder.cartItemRowBinding.tvQuantity.text = productItem.itemQty.toString()

        holder.cartItemRowBinding.imgRemove.setOnClickListener{
            cartClickCallback.onRemoveItemClick(productItem)
        }

    }

    interface CartClickCallback {
        fun onRemoveItemClick(productItem: ProductItem)
    }
}