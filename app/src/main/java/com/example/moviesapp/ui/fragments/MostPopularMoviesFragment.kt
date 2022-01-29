package com.example.moviesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.MovieAdapter
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMostPopularMoviesBinding
import com.example.moviesapp.extensions.hide
import com.example.moviesapp.extensions.show
import com.example.moviesapp.ui.MoviesActivity
import com.example.moviesapp.util.Resource
import com.example.moviesapp.viewmodel.MovieViewModel
import timber.log.Timber

class MostPopularMoviesFragment : Fragment(R.layout.fragment_most_popular_movies) {

    private var _binding: FragmentMostPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMostPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        movieViewModel = (requireActivity() as MoviesActivity).movieViewModel

        movieViewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.pbMostPopularMovies.show()
                }
                is Resource.Success -> {
                    resource.data?.let { moviesResponse ->
                        binding.pbMostPopularMovies.hide()
                        movieAdapter.submitList(moviesResponse.movies)
                    }
                }
                is Resource.Error -> {

                }
            }
        }

    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMostPopularMovies.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = movieAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}