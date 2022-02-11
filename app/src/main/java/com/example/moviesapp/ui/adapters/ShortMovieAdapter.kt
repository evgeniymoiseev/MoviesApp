package com.example.moviesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.moviesapp.databinding.SimpleItemMovieBinding
import com.example.moviesapp.model.local.ShortMovie

class ShortMovieAdapter(
    private val onMovieClick: (ShortMovie) -> Unit,
) :
    ListAdapter<ShortMovie, ShortMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShortMovie>() {
            override fun areItemsTheSame(oldItem: ShortMovie, newItem: ShortMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ShortMovie, newItem: ShortMovie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = SimpleItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding, onMovieClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class MovieViewHolder(
        private val binding: SimpleItemMovieBinding,
        private val onMovieClick: (ShortMovie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentMovie: ShortMovie? = null

        init {
            binding.root.setOnClickListener {
                currentMovie?.let { onMovieClick(it) }
            }
        }

        fun bind(shortMovie: ShortMovie) {
            currentMovie = shortMovie

            Glide.with(binding.root)
                .load(shortMovie.image)
                .transition(withCrossFade())
                .into(binding.ivPoster)
            binding.tvTitle.text = shortMovie.title
            binding.tvDescription.text = shortMovie.description
        }
    }
}