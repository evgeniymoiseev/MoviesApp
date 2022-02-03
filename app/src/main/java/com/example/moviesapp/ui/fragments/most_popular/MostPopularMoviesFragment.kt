package com.example.moviesapp.ui.fragments.most_popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMostPopularMoviesBinding
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.model.most_popular_movies.SimpleMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.adapters.MovieAdapter
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.util.Resource
import com.example.moviesapp.util.extensions.hide
import com.example.moviesapp.util.extensions.show
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class MostPopularMoviesFragment : BindingFragment<FragmentMostPopularMoviesBinding>() {

    private lateinit var mostPopularMovieViewModel: MostPopularMoviesViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMostPopularMoviesBinding =
        FragmentMostPopularMoviesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val database = MovieDatabase(requireActivity().applicationContext)
        val repository = MovieRepository(database)
        val factory =
            MostPopularMoviesViewModelFactory(requireActivity().application, repository)
        mostPopularMovieViewModel =
            ViewModelProvider(this, factory)[MostPopularMoviesViewModel::class.java]

        mostPopularMovieViewModel.mostPopularMovies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.pbMostPopularMovies.show()
                }
                is Resource.Success -> {
                    resource.data?.let { listMovies ->
                        binding.pbMostPopularMovies.hide()
                        movieAdapter.submitList(listMovies)
                    }
                }
                is Resource.Error -> {
                    Snackbar.make(view, resource.errorMessage!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onMovieClick(movie: SimpleMovie) {
        val bundle = Bundle().apply {
            putString("movieId", movie.id)
        }
        findNavController().navigate(
            R.id.action_mostPopularMoviesFragment_to_movieDetailFragment,
            bundle
        )
    }

    private fun onFavoriteClick(movie: SimpleMovie) {
        if (movie.isFavorite) {
            mostPopularMovieViewModel.deleteFavoriteMovie(movie.id)
        } else {
            mostPopularMovieViewModel.saveFavoriteMovie(movie.copy(isFavorite = true))
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(
            { movie -> onMovieClick(movie) },
            { movie -> onFavoriteClick(movie) }
        )
        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable, null))
        binding.rvMostPopularMovies.apply {
            adapter = movieAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }
}