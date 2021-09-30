package com.bosha.feature_main.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bosha.feature_main.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HostMenuFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(
            view.findViewById<BottomNavigationView>(R.id.bot_nav_bar),
            (childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment)
                .navController
        )
    }

}