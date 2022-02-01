package com.example.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        setupBottomNavigationView(binding)

        val repository = MovieRepository()
        val viewModelFactory = MovieViewModelFactory(application, repository)
        movieViewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }

    private fun setupBottomNavigationView(binding: ActivityMoviesBinding) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }

}