package com.bosha.feature_main.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bosha.feature_main.R
import com.bosha.utils.extensions.waitPreDraw
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.MaterialElevationScale

class HostMenuFragment : Fragment(R.layout.fragment_main_host) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(
            view.findViewById<BottomNavigationView>(R.id.bot_nav_bar),
            (childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment)
                .navController
        )
        waitPreDraw()
        enterTransition = MaterialElevationScale(true).apply { duration = 500 }
        reenterTransition = MaterialElevationScale(false).apply { duration = 500 }
    }

}