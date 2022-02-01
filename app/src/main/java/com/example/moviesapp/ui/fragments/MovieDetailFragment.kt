package com.example.moviesapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.ui.MoviesActivity
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.ui.viewmodels.MovieViewModel
import com.example.moviesapp.util.Resource
import com.google.android.material.chip.Chip
import timber.log.Timber

class MovieDetailFragment : BindingFragment<FragmentMovieDetailBinding>() {

    lateinit var viewModel: MovieViewModel
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailBinding = FragmentMovieDetailBinding
        .inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MoviesActivity).movieViewModel
        val movieId = args.movieId
        viewModel.getMovieById(movieId)
        viewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val movie = resource.data!!
                    Glide.with(view).load(movie.image).placeholder(R.drawable.ic_placeholder)
                        .into(binding.ivMovie)
                    binding.tvPlot.text = movie.plot
                    (activity as MoviesActivity).supportActionBar?.title = movie.fullTitle
                    movie.genres.split(", ").forEach {
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        val textView = TextView(context)
                        textView.text = it
                        textView.layoutParams = params
                        binding.llGenres?.addView(textView)
                    }
                }
                is Resource.Error -> {
                }
            }
        }
    }

}