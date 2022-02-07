package com.example.moviesapp.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSearchMoviesBinding
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.model.local.ShortMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.adapters.ShortMovieAdapter
import com.example.moviesapp.ui.fragments.base.BindingFragment
import com.example.moviesapp.util.Constants
import com.example.moviesapp.util.Event
import com.google.android.material.snackbar.Snackbar

class SearchFragment : BindingFragment<FragmentSearchMoviesBinding>() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var movieAdapter: ShortMovieAdapter

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchMoviesBinding =
        FragmentSearchMoviesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val database = MovieDatabase(requireActivity().applicationContext)
        val repository = MovieRepository(database)
        val factory = SearchViewModelFactory(
            requireActivity().application,
            repository
        )
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        binding.etMovies.addTextChangedListener { editable ->
            editable?.let {
                if (editable.toString()
                        .isNotEmpty()
                ) searchViewModel.searchMovies(editable.toString())
            }
        }

        searchViewModel.searchEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is Event.Loading -> {
                    movieAdapter.submitList(emptyList())
                    binding.pbSearch.visibility = View.VISIBLE
                    binding.tvNoResults.visibility = View.INVISIBLE
                }
                is Event.Success -> {
                    binding.pbSearch.visibility = View.GONE
                    movieAdapter.submitList(event.data!!)
                    binding.tvNoResults.visibility =
                        if (event.data.isNotEmpty()) View.INVISIBLE else View.VISIBLE
                }
                is Event.Error -> {
                    binding.pbSearch.visibility = View.GONE
                    movieAdapter.submitList(emptyList())
                    binding.tvNoResults.visibility = View.INVISIBLE
                    Snackbar.make(view, event.errorMessage!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onMovieClick(movie: ShortMovie) {
        if (movie.isMovie) {
            val bundle = Bundle().apply {
                putString(Constants.ID_BUNDLE_KEY, movie.id)
                putString(Constants.TITLE_BUNDLE_KEY, movie.title)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_movieDetailFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = ShortMovieAdapter { movie -> onMovieClick(movie) }

        val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_drawable,
                null
            )!!
        )
        binding.rvSearchMovies.apply {
            adapter = movieAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }
}