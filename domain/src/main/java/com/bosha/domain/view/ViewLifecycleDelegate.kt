package com.bosha.domain.view

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
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

    fun doInLifecycleScope(block: suspend CoroutineScope.() -> Unit) {
        logcat(LogPriority.ERROR) { lifecycleOwner?.lifecycle.toString() }

        lifecycle.coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        doOnCreateView?.invoke()
        logcat(LogPriority.ERROR) { "ON_CREATE $lifecycleOwner" }
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        doOnDestroyView?.invoke()
        logcat(LogPriority.ERROR) { "ON_DESTROY $lifecycleOwner" }
    }
}



