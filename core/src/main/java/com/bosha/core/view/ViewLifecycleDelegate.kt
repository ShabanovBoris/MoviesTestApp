package com.bosha.core.view

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.Delegates


class ViewLifecycleDelegate(
    private val doOnCreateView: (() -> Unit)? = null,
    private val doOnDestroyView: (() -> Unit)? = null,
) : DefaultLifecycleObserver {

    private var lifecycleOwner: LifecycleOwner? by Delegates.observable(null) { property, oldValue, newValue ->
        updateListener(oldValue, newValue)
    }
    private val lifecycle get() = checkNotNull(lifecycleOwner).lifecycle

    private fun updateListener(old: LifecycleOwner?, new: LifecycleOwner?) {
        old?.lifecycle?.removeObserver(this)
        new?.lifecycle?.addObserver(this)
    }

    operator fun invoke(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        doOnCreateView?.invoke()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        doOnDestroyView?.invoke()
    }
}



