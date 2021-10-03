package com.bosha.moviesdemo.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
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
        holder.binding.tvInfo.text = position.toString()
        holder.binding.bOk.isVisible = position == 2
    }

    override fun getItemCount(): Int = 3
}


class WelcomeViewHolder(val binding: OnboardPagerItemBinding) : RecyclerView.ViewHolder(binding.root)