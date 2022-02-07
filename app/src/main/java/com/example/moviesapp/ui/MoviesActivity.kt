package com.example.moviesapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMoviesBinding
import com.example.moviesapp.util.Constants.Companion.TITLE_BUNDLE_KEY

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.bottomNavigationView?.setupWithNavController(navController)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                R.id.mostPopularMoviesFragment -> {
                    binding.bottomNavigationView?.visibility = View.VISIBLE
                    binding.toolbar.title = getString(R.string.most_popular_movies)
                }
                R.id.favoriteMoviesFragment -> {
                    binding.bottomNavigationView?.visibility = View.VISIBLE
                    binding.toolbar.title = getString(R.string.watch_list)
                }
                R.id.searchFragment -> {
                    binding.bottomNavigationView?.visibility = View.VISIBLE
                    binding.toolbar.title = getString(R.string.search)
                }
                R.id.movieDetailFragment -> {
                    binding.bottomNavigationView?.visibility = View.GONE
                    binding.toolbar.title = args?.getString(TITLE_BUNDLE_KEY)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.fragmentContainer)
        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)
    }
}