package com.bosha.core.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

fun <T : ViewBinding> KClass<T>.inflateViewBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
): T {
    return members.filter { it.name == "inflate" }[1]
        .call(inflater, container, false) as T
}