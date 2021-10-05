package com.bosha.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

fun Activity.onNoInternet(onInternetAbsence: () -> Unit) {
    val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (manager.activeNetwork == null) onInternetAbsence()
}
