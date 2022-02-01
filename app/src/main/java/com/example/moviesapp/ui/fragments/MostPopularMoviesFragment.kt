package com.example.moviesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMostPopularMoviesBinding
import com.example.moviesapp.model.most_popular_movies.MostPopularMovie
import com.example.moviesapp.util.extensions.hide
import com.example.moviesapp.util.extensions.show
import com.example.moviesapp.ui.MoviesActivity
import com.example.moviesapp.ui.adapters.MovieAdapter
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.util.Resource
import com.example.moviesapp.ui.viewmodels.MovieViewModel

class MostPopularMoviesFragment : BindingFragment<FragmentMostPopularMoviesBinding>() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMostPopularMoviesBinding =
        FragmentMostPopularMoviesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        movieViewModel = (activity as MoviesActivity).movieViewModel

        movieViewModel.mostPopularMovies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.pbMostPopularMovies.show()
                }
                is Resource.Success -> {
                    resource.data?.let { moviesResponse ->
                        binding.pbMostPopularMovies.hide()
                        movieAdapter.submitList(moviesResponse.mostPopularMovies)
                    }
                }
                is Resource.Error -> {
                    TODO()
                }
            }
        }
    }

    private fun onMovieClick(mostPopularMovie: MostPopularMovie) {
        val bundle = Bundle().apply {
            putString("movieId", mostPopularMovie.id)
        }
        findNavController().navigate(
            R.id.action_mostPopularMoviesFragment_to_movieDetailFragment,
            bundle
        )
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie -> onMovieClick(movie) }
        binding.rvMostPopularMovies.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = movieAdapter
        }
    }
}