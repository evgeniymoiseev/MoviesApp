package com.example.moviesapp.ui.fragments.favorite

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
import com.example.moviesapp.databinding.FragmentFavoriteMoviesBinding
import com.example.moviesapp.model.local.SimpleMovie
import com.example.moviesapp.ui.adapters.MovieAdapter
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.util.Constants.Companion.ID_BUNDLE_KEY
import com.example.moviesapp.util.Constants.Companion.TITLE_BUNDLE_KEY
import com.example.moviesapp.util.extensions.appComponent
import javax.inject.Inject

class FavoriteFragment : BindingFragment<FragmentFavoriteMoviesBinding>() {

    @Inject
    lateinit var factory: FavoriteViewModelFactory
    private lateinit var movieAdapter: MovieAdapter
    private val favoriteMovieViewModel: FavoriteViewModel by viewModels { factory }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteMoviesBinding =
        FragmentFavoriteMoviesBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        favoriteMovieViewModel.getFavoriteMovies().observe(viewLifecycleOwner) { movieList ->
            movieAdapter.submitList(movieList)
        }
    }

    private fun onMovieClick(movie: SimpleMovie) {
        val bundle = Bundle().apply {
            putString(ID_BUNDLE_KEY, movie.id)
            putString(TITLE_BUNDLE_KEY, movie.title)
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
        dividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_drawable,
                null
            )!!
        )
        binding.rvFavoriteMovies.apply {
            adapter = movieAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }
}