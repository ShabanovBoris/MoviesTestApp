package com.bosha.domain.view

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class ViewLifecycleDelegate(
    private val doOnCreateView: (() -> Unit)? = null,
    private val doOnDestroyView: (() -> Unit)? = null,
) : LifecycleObserver {

    private var lifecycleOwner: LifecycleOwner? by Delegates.observable(null) { property, oldValue, newValue ->
        updateListener(oldValue, newValue)
    }

    private fun updateListener(old: LifecycleOwner?, new: LifecycleOwner?) {
        old?.lifecycle?.removeObserver(this)
        new?.lifecycle?.addObserver(this)
    }

    operator fun invoke(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun doInLifecycleScope(block: suspend CoroutineScope.() -> Unit) {
        val lifecycle = lifecycleOwner?.lifecycle ?: return

        lifecycle.coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                block()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateView() {
        doOnCreateView?.invoke()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() {
        doOnDestroyView?.invoke()
    }
}



