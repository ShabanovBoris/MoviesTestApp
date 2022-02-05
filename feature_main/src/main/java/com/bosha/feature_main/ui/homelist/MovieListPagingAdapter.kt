package com.bosha.feature_main.ui.homelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bosha.core_domain.entities.Movie
import com.bosha.feature_main.R
import com.bosha.feature_main.databinding.MovieItemBinding
import com.bosha.feature_main.ui.favorite.DiffCallback


class MovieListPagingAdapter(private val onClick: (card: View) -> Unit) :
    PagingDataAdapter<Movie, MovieListPagingAdapter.ViewHolderPagerMovie>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPagerMovie {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolderPagerMovie(view).apply {
            binding.movieCard.setOnClickListener(onClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderPagerMovie, position: Int) {
        val item = getItem(position) ?: return
        holder.bindData(item)
    }

    class ViewHolderPagerMovie(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

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

