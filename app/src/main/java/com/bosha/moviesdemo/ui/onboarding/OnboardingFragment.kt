package com.bosha.moviesdemo.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bosha.core.navigation.navigate
import com.bosha.moviesdemo.R
import com.bosha.moviesdemo.databinding.FragmentOnboardBinding
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment(), OnboardingScreen by OnboardingScreen.Base() {

    init {
        setFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return showOnboardingIfNeed(
            onSkip = {
                toMainScreen()
                null
            },
            onShow = {
                FragmentOnboardBinding.inflate(inflater, container, false)
                    .apply(::setUpViewPager).root
            }
        )
    }

    override fun onDestroyView() {
        clearRef()
        super.onDestroyView()
    }

    private fun setUpViewPager(binding: FragmentOnboardBinding) {
        binding.viewPager.adapter = OnboardingPagerAdapter { toMainScreen() }
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, _ ->
            tab.setIcon(R.drawable.tab_oval)
        }.attach()
    }

    private fun toMainScreen() {
        navigate {
            target = NavCommand(Screens.MAIN)
            options {
                popUpTo(R.id.nav_graph_application)
            }
        }
    }
}