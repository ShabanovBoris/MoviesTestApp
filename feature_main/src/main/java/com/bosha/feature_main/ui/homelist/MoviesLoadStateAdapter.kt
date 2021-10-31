package com.bosha.feature_main.ui.homelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bosha.feature_main.R
import com.bosha.feature_main.databinding.MovieLoadingFooterItemBinding

class MoviesLoadStateAdapter(
    private val onLoaded: (() -> Unit)? = null,
    private val onError: () -> Unit,
    ) :
    LoadStateAdapter<MoviesLoadStateAdapter.LoadViewHolder>() {

    class LoadViewHolder(val binding: MovieLoadingFooterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.NotLoading -> {
                onError()
            }
            LoadState.Loading -> {
                holder.binding.item1.ivImage.setImageResource(R.drawable.ic_loading_image)
                holder.binding.item2.ivImage.setImageResource(R.drawable.ic_loading_image)
            }
            is LoadState.Error -> {
                holder.binding.item1.ivImage.setImageResource(R.drawable.ic_baseline_error_outline_24)
                holder.binding.item2.ivImage.setImageResource(R.drawable.ic_baseline_error_outline_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val view = MovieLoadingFooterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).also {
            it.root.layoutParams.width = parent.width
        }

        return LoadViewHolder(view)
    }
}