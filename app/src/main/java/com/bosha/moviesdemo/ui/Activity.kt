package com.bosha.moviesdemo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.contextaware.withContextAvailable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.withCreated
import com.bosha.core.whenNoInternet
import com.bosha.moviesdemo.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        whenNoInternet {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
        lifecycle.currentState
        lifecycle.coroutineScope.launch {
            lifecycle.withCreated {  }
            withContextAvailable {

            }
        }
    }
}