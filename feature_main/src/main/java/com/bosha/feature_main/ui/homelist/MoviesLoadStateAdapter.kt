package com.bosha.feature_main.ui.homelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bosha.feature_main.R
import com.bosha.feature_main.databinding.MovieItemBinding

class MoviesLoadStateAdapter: LoadStateAdapter<MoviesLoadStateAdapter.LoadViewHolder>() {

    class LoadViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        when(loadState){
            is LoadState.NotLoading -> {}
            LoadState.Loading -> {
                holder.binding.ivImage.setImageResource(R.drawable.ic_search)
            }
            is LoadState.Error -> {}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadViewHolder(view)
    }
}