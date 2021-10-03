package com.bosha.moviesdemo.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bosha.moviesdemo.R
import com.bosha.moviesdemo.databinding.FragmentOnboardBinding
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.bosha.utils.navigation.navigate
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentOnboardBinding.inflate(inflater, container, false).apply {
            setUpViewPager(this)
        }.root

    private fun setUpViewPager(binding: FragmentOnboardBinding) {

        binding.viewPager.adapter = OnboardingPagerAdapter {
            navigate {
                target = NavCommand(Screens.MAIN)
                options {
                    popUpTo(R.id.nav_graph_application)
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, _ ->
            tab.setIcon(R.drawable.tab_oval)
        }.attach()
    }
}