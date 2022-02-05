package com.bosha.core

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

fun Activity.whenNoInternet(onInternetAbsence: () -> Unit) {
    val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (manager.activeNetwork == null) onInternetAbsence()
}
