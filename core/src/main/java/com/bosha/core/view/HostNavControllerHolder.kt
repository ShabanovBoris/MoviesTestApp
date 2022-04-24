package com.bosha.core.view

import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding

interface HostNavControllerHolder {
    fun getHostNavController() : NavController
    fun getHostNavGraphId(): Int
}

fun <B: ViewBinding, V: BaseViewModel> BaseFragment<B, V>.getHostNavGraphId(): Int {
    return (requireActivity() as HostNavControllerHolder).getHostNavGraphId()
}