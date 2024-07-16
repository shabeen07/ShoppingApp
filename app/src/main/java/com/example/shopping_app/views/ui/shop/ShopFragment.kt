package com.example.shopping_app.views.ui.shop

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_app.MyApp
import com.example.shopping_app.R
import com.example.shopping_app.databinding.FragmentShopBinding
import com.example.shopping_app.models.NetworkResponse
import com.example.shopping_app.models.ProductItem
import com.example.shopping_app.respository.local.CartRepository
import com.example.shopping_app.views.ui.shop.adapter.ShopItemAdapter
import com.google.android.material.snackbar.Snackbar

class ShopFragment : Fragment(), ShopItemAdapter.ItemClickCallback {

    private lateinit var fragmentShopBinding: FragmentShopBinding
    private lateinit var navController: NavController
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var cartRepository: CartRepository

    companion object {
        private const val TAG = "ShopFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentShopBinding = FragmentShopBinding.inflate(inflater, container, false)
        shopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        cartRepository = (requireActivity().application as MyApp).repository
        return fragmentShopBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get nav controller
        navController = Navigation.findNavController(view)
        // Add options menu to the toolbar
        setupMenu()
        initUi()
        // observe products
        shopViewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> {
                    Log.d(TAG, "Error: ${it.message}")
                    viewProgressBar(false)
                }

                is NetworkResponse.Loading -> {
                    Log.d(TAG, "Loading: ")
                    viewProgressBar(true)
                }

                is NetworkResponse.Success -> {
                    Log.d(TAG, "Success: ")
                    // hide progress bar
                    viewProgressBar(false)
                    // set adapter
                    setShopAdapter(it.data)
                }
            }
        }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.shop_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.cartFragment -> {
                        // navigate to cart fragment
                        navController.navigate(R.id.action_shopFragment_to_cartFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initUi() {
        fragmentShopBinding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        fragmentShopBinding.rvProducts.itemAnimator = DefaultItemAnimator()
        fragmentShopBinding.rvProducts.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        fragmentShopBinding.swipeRefreshLayout.setOnRefreshListener {
            // load products
            Handler(Looper.getMainLooper()).postDelayed({
                shopViewModel.getProducts()

                fragmentShopBinding.swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }
    }

    private fun viewProgressBar(isShow: Boolean) {
        fragmentShopBinding.progressHorizontal.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun setShopAdapter(productList: List<ProductItem>) {
        productList.map {
            it.itemQty = 1
        }
        val shopAdapter = ShopItemAdapter(requireContext(), productList, this)
        fragmentShopBinding.rvProducts.adapter = shopAdapter
    }

    override fun onAddItemClick(productItem: ProductItem) {
        shopViewModel.insertItem(cartRepository, productItem)
        Snackbar.make(requireView(), "Item Added!", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shopViewModel.products.removeObservers(viewLifecycleOwner)
    }

}