package com.example.moviesapp.ui.fragments.most_popular

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.MovieRepository

class MostPopularViewModelFactory(
    private val app: Application,
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MostPopularViewModel(app, repository) as T
    }
}