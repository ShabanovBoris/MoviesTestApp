package com.bosha.feature_search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bosha.core_domain.entities.Movie
import com.bosha.feature_search.R
import com.bosha.feature_search.databinding.MovieSearchItemBinding


class MovieListAdapter(private val onClick: (View) -> Unit) :
    ListAdapter<Movie, MovieListAdapter.ViewHolderMovie>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMovie {
        val view =
            MovieSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolderMovie(view).apply {
            binding.movieCard.setOnClickListener(onClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderMovie, position: Int) {
        holder.bindData(getItem(position))
    }


    class ViewHolderMovie(val binding: MovieSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(movie: Movie) = binding.apply {

            movieCard.transitionName = movie.id.toString()
            ivImage.load(movie.imageUrl) {
                allowHardware(false)
                crossfade(true)
                placeholder(R.drawable.ic_loading_image)
            }

            tvTitleMovie.text = movie.title

            tvGenre.text = ""
            for (g in movie.genres) {
                tvGenre.append(g.name + " ")
            }
            rbRating.rating = movie.rating.toFloat()
            tvRelease.text = movie.releaseDate
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem

}