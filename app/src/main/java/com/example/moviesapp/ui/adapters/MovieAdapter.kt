package com.example.moviesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.model.most_popular_movies.MostPopularMovie

val diffUtilCallback = object : DiffUtil.ItemCallback<MostPopularMovie>() {
    override fun areItemsTheSame(oldItem: MostPopularMovie, newItem: MostPopularMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MostPopularMovie, newItem: MostPopularMovie): Boolean {
        return oldItem == newItem
    }
}

class MovieAdapter(private val onMovieClick: (MostPopularMovie) -> Unit) :
    ListAdapter<MostPopularMovie, MovieAdapter.MovieViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onMovieClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onMovieClick: (MostPopularMovie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentMostPopularMovie: MostPopularMovie? = null

        init {
            binding.ivPoster.setOnClickListener {
                currentMostPopularMovie?.let {
                    onMovieClick(it)
                }
            }
        }

        fun bind(mostPopularMovie: MostPopularMovie) {
            Glide.with(binding.root).load(mostPopularMovie.image).into(binding.ivPoster)
            currentMostPopularMovie = mostPopularMovie
        }
    }
}