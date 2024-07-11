package com.example.shopping_app.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_app.MyApp
import com.example.shopping_app.databinding.FragmentCartBinding
import com.example.shopping_app.models.ProductItem
import com.example.shopping_app.ui.cart.adapter.CartItemAdapter
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode


class CartFragment : Fragment(), CartItemAdapter.CartClickCallback {

    private lateinit var fragmentCartBinding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel = ViewModelProvider(
            this,
            CartViewModelFactory((requireActivity().application as MyApp).repository)
        )[CartViewModel::class.java]
        return fragmentCartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        cartViewModel.cartItems.observe(viewLifecycleOwner) {
            updateUi(it)
            setCartAdapter(it)
        }
        fragmentCartBinding.btnCheckout.setOnClickListener{
            cartViewModel.clearCart()
            Snackbar.make(requireView(), "Cart Cleared!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun updateUi(productList: List<ProductItem>) {
        // calculate total amount and update ui
        val totalAmount = "$" + productList.sumOf { it.getTotalAmount() }
        val taxTotal = "$" + productList.sumOf { it.getTaxAmount() }
        val subTotal = "$" + productList.sumOf { it.getSubTotal() }
        fragmentCartBinding.tvTotal.text = totalAmount
        fragmentCartBinding.tvTaxTotal.text = taxTotal
        fragmentCartBinding.tvSubTotal.text = subTotal
    }

    private fun initUi() {
        fragmentCartBinding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        fragmentCartBinding.rvProducts.itemAnimator = DefaultItemAnimator()
        fragmentCartBinding.rvProducts.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }


    private fun setCartAdapter(productList: List<ProductItem>) {
        val cartItemAdapter = CartItemAdapter(requireContext(), productList, this)
        fragmentCartBinding.rvProducts.adapter = cartItemAdapter
    }

    // remove item from cart
    override fun onRemoveItemClick(productItem: ProductItem) {
        cartViewModel.removeCartItem(productItem.itemID)
        Snackbar.make(requireView(), "Item Removed!", Snackbar.LENGTH_SHORT).show()
    }
}