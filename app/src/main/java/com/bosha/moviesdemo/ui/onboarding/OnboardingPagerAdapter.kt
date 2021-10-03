package com.bosha.moviesdemo.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bosha.moviesdemo.R
import com.bosha.moviesdemo.databinding.OnboardPagerItemBinding

class OnboardingPagerAdapter(private val onOkClick: (View) -> Unit) : RecyclerView.Adapter<WelcomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder {
        val binding =
            OnboardPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WelcomeViewHolder(binding).apply {
            binding.bOk.setOnClickListener(onOkClick)
        }
    }

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        when(position){
            0 -> {
                holder.binding.tvInfo.setText(R.string.search_spalsh_text)
                holder.binding.ivMain.setImageResource(R.drawable.ic_outline_movie)
            }
            1 -> {
                holder.binding.tvInfo.setText(R.string.favorites_spalsh_text)
                holder.binding.ivMain.setImageResource(R.drawable.ic_favorite)
            }
            2 -> {
                holder.binding.tvInfo.setText(R.string.schedule_splash_text)
                holder.binding.ivMain.setImageResource(R.drawable.ic_schedule_24)
            }
        }
        holder.binding.bOk.isVisible = position == 2
    }

    override fun getItemCount(): Int = 3
}


class WelcomeViewHolder(val binding: OnboardPagerItemBinding) : RecyclerView.ViewHolder(binding.root)