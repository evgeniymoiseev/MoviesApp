package com.example.moviesapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.databinding.ActivityMoviesBinding
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.viewmodel.MovieViewModel
import com.example.moviesapp.viewmodel.MovieViewModelFactory

class MoviesActivity : AppCompatActivity() {
    lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupStatusBar()

        val repository = MovieRepository()
        val viewModelFactory = MovieViewModelFactory(application, repository)
        movieViewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }

    private fun setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }
}