package com.example.moviesapp.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentFavoriteMoviesBinding
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.model.SimpleMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.adapters.MovieAdapter
import com.example.moviesapp.ui.fragments.base.BindingFragment

class FavoriteMoviesFragment : BindingFragment<FragmentFavoriteMoviesBinding>() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var favoriteMovieViewModel: FavoriteMoviesViewModel

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteMoviesBinding =
        FragmentFavoriteMoviesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val database = MovieDatabase(requireActivity().applicationContext)
        val repository = MovieRepository(database)
        val factory = FavoriteMoviesViewModelFactory(repository)
        favoriteMovieViewModel =
            ViewModelProvider(this, factory)[FavoriteMoviesViewModel::class.java]
        favoriteMovieViewModel.getFavoriteMovies().observe(viewLifecycleOwner) { movieList ->
            movieAdapter.submitList(movieList)
        }
    }

    private fun onMovieClick(movie: SimpleMovie) {
        val bundle = Bundle().apply {
            putString("movieId", movie.id)
        }
        findNavController().navigate(
            R.id.action_favoriteMoviesFragment_to_movieDetailFragment,
            bundle
        )
    }

    private fun onFavoriteClick(movie: SimpleMovie) {
        favoriteMovieViewModel.deleteFavoriteMovie(movie.id)
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(
            { movie -> onMovieClick(movie) },
            { movie -> onFavoriteClick(movie) }
        )
        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable, null))
        binding.rvFavoriteMovies.apply {
            adapter = movieAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }
}