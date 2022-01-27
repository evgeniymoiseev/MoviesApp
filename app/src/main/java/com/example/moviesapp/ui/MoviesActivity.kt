package com.example.moviesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.R
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

        val repository = MovieRepository()
        val viewModelFactory = MovieViewModelFactory(application, repository)
        movieViewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }
}