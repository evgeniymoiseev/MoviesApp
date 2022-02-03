package com.example.moviesapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.model.SimpleMovie

val diffUtilCallback = object : DiffUtil.ItemCallback<SimpleMovie>() {
    override fun areItemsTheSame(oldItem: SimpleMovie, newItem: SimpleMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SimpleMovie, newItem: SimpleMovie): Boolean {
        return oldItem == newItem
    }
}

class MovieAdapter(
    private val onMovieClick: (SimpleMovie) -> Unit,
    private val onFavoriteClick: (SimpleMovie) -> Unit
) :
    ListAdapter<SimpleMovie, MovieAdapter.MovieViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onMovieClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val onMovieClick: (SimpleMovie) -> Unit,
        private val onFavoriteClick: (SimpleMovie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentMovie: SimpleMovie? = null

        init {
            binding.root.setOnClickListener {
                currentMovie?.let { onMovieClick(it) }
            }
            binding.ivFavorite.setOnClickListener {
                currentMovie?.let { onFavoriteClick(it) }
            }
        }

        fun bind(simpleMovie: SimpleMovie) {
            currentMovie = simpleMovie

            Glide.with(binding.root).load(simpleMovie.imageSrc).into(binding.ivPoster)
            binding.ivRankUpDown.setImageResource(simpleMovie.upDownDrawable)
            binding.tvRankUpDown.text = simpleMovie.rank
            binding.tvTitle.text = simpleMovie.title
            binding.tvYear.text = simpleMovie.year
            binding.tvRating.text = simpleMovie.rating
            binding.ivStar.visibility = if (simpleMovie.isEmptyRating) View.GONE else View.VISIBLE
            binding.ivFavorite.setImageResource(
                if (simpleMovie.isFavorite) {
                    R.drawable.ic_added_bookmark
                } else {
                    R.drawable.ic_add_bookmark
                }
            )
        }
    }
}