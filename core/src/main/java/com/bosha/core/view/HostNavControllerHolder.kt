package com.bosha.core.view

import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding

interface HostNavControllerHolder {
    fun getNavController() : NavController
    fun getHostNavGraph(): Int
}

fun <B: ViewBinding, V: BaseViewModel> BaseFragment<B, V>.getNavGraphHost(): Int {
    return (requireActivity() as HostNavControllerHolder).getHostNavGraph()
}