package com.example.moviesapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMoviesBinding
import com.example.moviesapp.util.Constants.Companion.TITLE_BUNDLE_KEY

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                R.id.mostPopularMoviesFragment -> {
                    binding.bottomNavigationView?.visibility = View.VISIBLE
                    supportActionBar?.title = getString(R.string.most_popular_movies)
                }
                R.id.favoriteMoviesFragment -> {
                    binding.bottomNavigationView?.visibility = View.VISIBLE
                    supportActionBar?.title = getString(R.string.watch_list)
                }
                R.id.searchFragment -> {
                    binding.bottomNavigationView?.visibility = View.VISIBLE
                    supportActionBar?.title = getString(R.string.search)
                }
                R.id.movieDetailFragment -> {
                    binding.bottomNavigationView?.visibility = View.GONE
                    supportActionBar?.title = args?.getString(TITLE_BUNDLE_KEY)
                }
            }
        }

        if (binding.drawerLayout != null) {
            binding.navigationView?.setupWithNavController(navController)
            val toggle = ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                R.string.directors,
                R.string.stars
            )
            binding.drawerLayout?.addDrawerListener(toggle)
            toggle.syncState()
        }
    }
}