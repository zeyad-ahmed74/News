package com.example.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()

    }

    private fun setupBottomNav(){

        //binding.bottomNav.setupWithNavController(binding.navPostGraph.findNavController())
          val navController = this.findNavController(R.id.nav_post_graph)
          binding.bottomNav.setupWithNavController(navController)

          navController.addOnDestinationChangedListener{ _, destination , _ ->

            when(destination.id){
                R.id.splashFragment -> hideBottomNav()
                else -> showBottomNav()

            }
          }
    }


    private fun showBottomNav() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNav.visibility = View.GONE
    }
}