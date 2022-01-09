package com.bosha.moviesdemo.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.bosha.moviesdemo.R
import com.bosha.utils.onNoInternet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        onNoInternet {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }


        val root = WindowCompat.requireViewById<ViewGroup>(window, R.id.nav_host_fragment_container)
        val controller = WindowCompat.getInsetsController(window, root)

        // tell the window that we want to handle/fit any system windows
//        WindowCompat.setDecorFitsSystemWindows(window, false)

//        controller?.show(WindowInsetsCompat.Type.navigationBars())

//        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
//            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
//
//
////            WindowInsetsCompat.Builder().setInsets(
////                WindowInsetsCompat.Type.navigationBars(), Insets.of(
////                    0,
////                    0,
////                    0,
////                    navigationBarInsets.bottom
////                )
////            ).build()
//            insets
//        }
    }


}