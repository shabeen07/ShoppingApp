package com.example.shopping_app.ui.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_app.databinding.ShopItemRowBinding
import com.example.shopping_app.models.ProductItem

class ShopItemAdapter(
    private val context: Context, private var productList: List<ProductItem>,
    private val itemClickCallback: ItemClickCallback
) :
    RecyclerView.Adapter<ShopItemAdapter.MyHolder>() {

    class MyHolder(val shopItemRowBinding: ShopItemRowBinding) : RecyclerView.ViewHolder(
        shopItemRowBinding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ShopItemRowBinding.inflate(layoutInflater, parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val productItem = productList[position]
        holder.shopItemRowBinding.itemName.text = productItem.itemName
        val price = "$"+ productItem.sellingPrice
        holder.shopItemRowBinding.price.text = price
        val tax = "Tax: "+ productItem.taxPercentage.toString() +"%"
        holder.shopItemRowBinding.taxPercentage.text = tax

        holder.shopItemRowBinding.addToCart.setOnClickListener {
            itemClickCallback.onAddItemClick(productItem)
        }

        // set quantity
        holder.shopItemRowBinding.qtyLayout.tvQuantity.text = productItem.itemQty.toString()

        // increment quantity
        holder.shopItemRowBinding.qtyLayout.btnIncrement.setOnClickListener {
            incrementQuantity(productItem, position)
        }

        // decrement quantity
        holder.shopItemRowBinding.qtyLayout.btnDecrement.setOnClickListener {
            decrementQuantity(productItem, position)
        }
    }

    private fun incrementQuantity(productItem: ProductItem, position: Int) {
        productItem.itemQty += 1
        // update item quantity
        notifyItemChanged(position)
    }

    private fun decrementQuantity(productItem: ProductItem, position: Int) {
        if (productItem.itemQty > 1) {
            productItem.itemQty -= 1
            // update item quantity
            notifyItemChanged(position)
        }
    }

    // add to cart callback
    interface ItemClickCallback {
        fun onAddItemClick(productItem: ProductItem)
    }
}