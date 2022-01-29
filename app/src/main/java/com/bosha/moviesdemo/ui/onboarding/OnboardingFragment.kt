package com.bosha.moviesdemo.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.bosha.moviesdemo.R
import com.bosha.moviesdemo.databinding.FragmentOnboardBinding
import com.bosha.utils.navigation.NavCommand
import com.bosha.utils.navigation.Screens
import com.bosha.utils.navigation.navigate
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isFirstLaunch().not()) {
            toMainScreen()
            return null
        }
        setPrefValue()
        return FragmentOnboardBinding.inflate(inflater, container, false)
            .apply(::setUpViewPager).root
    }

    private fun setUpViewPager(binding: FragmentOnboardBinding) {
        binding.viewPager.adapter = OnboardingPagerAdapter {
            toMainScreen()
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, _ ->
            tab.setIcon(R.drawable.tab_oval)
        }.attach()
    }

    private fun isFirstLaunch() = requireActivity()
        .getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
        .getBoolean(KEY_IS_FIRST_LAUNCH, true)

    private fun setPrefValue() {
        requireActivity()
            .getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE).edit {
                putBoolean(KEY_IS_FIRST_LAUNCH, false)
            }
    }

    private fun toMainScreen(){
        navigate {
            target = NavCommand(Screens.MAIN)
            options {
                popUpTo(R.id.nav_graph_application)
            }
        }
    }

    private companion object {
        const val KEY_PREF = "key_prefs_first_launch"
        const val KEY_IS_FIRST_LAUNCH = "key_prefs_first_launch"
    }
}