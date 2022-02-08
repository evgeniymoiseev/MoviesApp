package com.example.moviesapp.ui.fragments.most_popular

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.MovieRepository

class MostPopularViewModelFactory(
    private val application: Application,
    private val repository: MovieRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MostPopularViewModel(application, repository) as T
    }
}