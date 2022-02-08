package com.example.moviesapp.ui.fragments.most_popular

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMostPopularMoviesBinding
import com.example.moviesapp.model.local.SimpleMovie
import com.example.moviesapp.ui.adapters.MovieAdapter
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.util.Constants.Companion.ID_BUNDLE_KEY
import com.example.moviesapp.util.Constants.Companion.TITLE_BUNDLE_KEY
import com.example.moviesapp.util.Event
import com.example.moviesapp.util.appComponent
import com.example.moviesapp.util.hide
import com.example.moviesapp.util.show
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MostPopularFragment : BindingFragment<FragmentMostPopularMoviesBinding>() {

    @Inject
    lateinit var factory: MostPopularViewModelFactory
    private val mostPopularViewModel: MostPopularViewModel by viewModels { factory }
    private lateinit var movieAdapter: MovieAdapter

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMostPopularMoviesBinding =
        FragmentMostPopularMoviesBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        mostPopularViewModel.getMostPopularMovies()

        mostPopularViewModel.mostPopularMovies.observe(viewLifecycleOwner) { event ->
            when (event) {
                is Event.Loading -> {
                    binding.pbMostPopularMovies.show()
                }
                is Event.Success -> {
                    event.data?.let { listMovies ->
                        binding.pbMostPopularMovies.hide()
                        movieAdapter.submitList(listMovies)
                    }
                }
                is Event.Error -> {
                    Snackbar.make(view, event.errorMessage!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onMovieClick(movie: SimpleMovie) {
        val bundle = Bundle().apply {
            putString(ID_BUNDLE_KEY, movie.id)
            putString(TITLE_BUNDLE_KEY, movie.title)
        }
        findNavController().navigate(
            R.id.action_mostPopularMoviesFragment_to_movieDetailFragment,
            bundle
        )
    }

    private fun onFavoriteClick(movie: SimpleMovie) {
        if (movie.isFavorite) {
            mostPopularViewModel.deleteFavoriteMovie(movie.id)
        } else {
            mostPopularViewModel.saveFavoriteMovie(movie.copy(isFavorite = true))
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(
            { movie -> onMovieClick(movie) },
            { movie -> onFavoriteClick(movie) }
        )
        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_drawable,
                null
            )!!
        )
        binding.rvMostPopularMovies.apply {
            adapter = movieAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }
}