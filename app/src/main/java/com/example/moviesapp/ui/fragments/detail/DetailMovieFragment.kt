package com.example.moviesapp.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.model.ExtendedMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.util.Resource

class DetailMovieFragment : BindingFragment<FragmentMovieDetailBinding>() {

    private val args: DetailMovieFragmentArgs by navArgs()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailBinding = FragmentMovieDetailBinding
        .inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isFavorite = false
        val database = MovieDatabase(requireActivity().applicationContext)
        val repository = MovieRepository(database)
        val factory = DetailMovieViewModelFactory(
            requireActivity().application,
            repository,
            args.movieId
        )
        val detailMovieViewModel = ViewModelProvider(
            this,
            factory
        )[DetailMovieViewModel::class.java]

        detailMovieViewModel.movieResource.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    TODO()
                }
                is Resource.Success -> {
                    resource.data?.let { movie ->
                        bindLayout(movie)
                    }
                }
                is Resource.Error -> {
                    TODO()
                }
            }
        }

        detailMovieViewModel.movieInDatabase.observe(viewLifecycleOwner) {
            isFavorite = it != null
        }
    }

    private fun bindLayout(movie: ExtendedMovie) {
        binding.tvTitle.text = movie.title
        binding.tvYear.text = movie.year
        binding.tvRuntime.text = movie.runtime
        Glide.with(requireView()).load(movie.poster).into(binding.ivPoster)
        binding.ivStar.visibility = if (movie.isEmptyRating) View.INVISIBLE else View.VISIBLE
        binding.tvRating.text = if (movie.isEmptyRating) "" else "${movie.rating}/10"
        binding.tvDirectors.text = movie.directors
        binding.tvStars.text = movie.stars
        binding.tvPlot.text = movie.plot
    }
}