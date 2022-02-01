package com.example.moviesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.ui.fragments.base.BindingFragment

class MovieDetailFragment : BindingFragment<FragmentMovieDetailBinding>() {

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailBinding = FragmentMovieDetailBinding
        .inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movieArg
        Glide.with(view).load(movie.image).into(binding.ivMovie)
    }
}