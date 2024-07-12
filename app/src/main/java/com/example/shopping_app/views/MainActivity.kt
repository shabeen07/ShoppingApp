package com.example.shopping_app.views

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.shopping_app.R
import com.example.shopping_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // set action bar
        setSupportActionBar(activityMainBinding.toolbar)

        // setup navigation
        setUpNavigation(activityMainBinding.toolbar)

        
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               if (navController.currentDestination?.id == R.id.shopFragment){
                   finish()
               } else {
                   navController.navigateUp()
               }
            }
        })

    }

    private fun setUpNavigation(toolbar: Toolbar) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(toolbar, navController)
    }

    // handle back press
    override fun onSupportNavigateUp(): Boolean {
        if (navController.currentDestination?.id != R.id.shopFragment){
            navController.navigateUp()
        }
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }

}