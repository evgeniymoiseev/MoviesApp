package com.example.moviesapp.ui.fragments.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.model.local.ExtendedMovie
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.util.Event
import com.example.moviesapp.util.extensions.appComponent
import com.example.moviesapp.util.extensions.hide
import com.example.moviesapp.util.extensions.show
import com.example.moviesapp.util.mappers.LocalExtendedToLocalSimpleMapper
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DetailMovieFragment : BindingFragment<FragmentMovieDetailBinding>() {

    @Inject
    lateinit var factory: DetailMovieViewModelFactory.AssistFactory
    private val args: DetailMovieFragmentArgs by navArgs()
    private val detailMovieViewModel: DetailMovieViewModel by viewModels {
        factory.create(args.movieId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailBinding = FragmentMovieDetailBinding
        .inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailMovieViewModel.movieResource.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Event.Loading -> {
                    binding.pbDetailLoading.show()
                }
                is Event.Success -> {
                    binding.pbDetailLoading.hide()
                    resource.data?.let { movie ->
                        bindLayout(movie)
                    }
                }
                is Event.Error -> {
                    binding.pbDetailLoading.hide()
                    Snackbar.make(view, resource.errorMessage!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        detailMovieViewModel.movieInDatabase.observe(viewLifecycleOwner) {
            binding.btAdd.isEnabled = it == null
        }
    }

    private fun bindLayout(movie: ExtendedMovie) {
        binding.tvTitle.text = movie.title
        binding.tvYear.text = movie.year
        binding.tvRuntime.text = movie.runtime
        Glide.with(requireView()).load(movie.imageSrc).into(binding.ivPoster)
        binding.ivStar.visibility = if (movie.isEmptyRating) View.INVISIBLE else View.VISIBLE
        binding.tvRating.text = if (movie.isEmptyRating) "" else "${movie.rating}/10"
        binding.tvDirectors.text = movie.directors
        binding.tvStars.text = movie.stars
        binding.tvPlot.text = movie.plot
        binding.btAdd.setOnClickListener {
            detailMovieViewModel.saveMovie(
                LocalExtendedToLocalSimpleMapper(isFavorite = true).map(
                    movie
                )
            )
        }
    }
}