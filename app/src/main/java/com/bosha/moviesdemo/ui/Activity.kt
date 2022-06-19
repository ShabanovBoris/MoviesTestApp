package com.bosha.moviesdemo.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bosha.core.view.HostNavControllerHolder
import com.bosha.core.whenNoInternet
import com.bosha.moviesdemo.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class Activity : AppCompatActivity(), HostNavControllerHolder {

    @Inject
    lateinit var fpsMeter: FpsMeter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        whenNoInternet {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
        val fpsTv = findViewById<TextView>(R.id.tv_fps_meter)
        fpsMeter.observeFps(this) {
            fpsTv.text = it.toString()
        }
    }

    override fun getHostNavController(): NavController {
        return findNavController(R.id.nav_graph_application)
    }

    override fun getHostNavGraphId(): Int {
        return R.id.nav_graph_application
    }
}