package com.example.moviesapp.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMoviesBinding
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.viewmodels.MovieViewModel
import com.example.moviesapp.ui.viewmodels.MovieViewModelFactory

class MoviesActivity : AppCompatActivity() {
    lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.movieDetailFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        val repository = MovieRepository()
        val viewModelFactory = MovieViewModelFactory(application, repository)
        movieViewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }


}