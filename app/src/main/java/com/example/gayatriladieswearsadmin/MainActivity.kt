package com.example.gayatriladieswearsadmin

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.gayatriladieswearsadmin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration:AppBarConfiguration
    lateinit var listner:NavController.OnDestinationChangedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHostFragment = supportFragmentManager.findFragmentById(com.example.gayatriladieswearsadmin.R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph)



        setupActionBarWithNavController(navController,appBarConfiguration)

        listner = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == com.example.gayatriladieswearsadmin.R.id.pendingOrderFragment){
                supportActionBar?.setTitle("Pending Orders")
            }else if(destination.id == com.example.gayatriladieswearsadmin.R.id.confirmOrderFragment){
                supportActionBar?.setTitle("Confirmed Orders")
            }else if(destination.id == com.example.gayatriladieswearsadmin.R.id.addProductFragment){
                supportActionBar?.setTitle("Add New Product")
            }else if(destination.id == com.example.gayatriladieswearsadmin.R.id.editProductFragment){
                supportActionBar?.setTitle("Products")
            }else if(destination.id == com.example.gayatriladieswearsadmin.R.id.editProductDetialFragment){
                supportActionBar?.setTitle("Edit Product")
            }else if(destination.id == com.example.gayatriladieswearsadmin.R.id.addStockFragment){
                supportActionBar?.setTitle("Low Stock Products")
            }else if(destination.id == com.example.gayatriladieswearsadmin.R.id.orderDetailFragment){
                supportActionBar?.setTitle("Check Order")
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(com.example.gayatriladieswearsadmin.R.id.fragment)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}