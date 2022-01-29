package com.bosha.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Класс для создания евента
 */
class Event<T> {
    private val sharedFlow = MutableSharedFlow<T>(extraBufferCapacity = 0)

    val flow get() = sharedFlow.asSharedFlow()

    internal fun emit(data: T) {
        sharedFlow.tryEmit(data)
    }
}

fun <T> Fragment.observe(event: Event<T>, action: suspend (T) -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            event.flow.collect(action)
        }
    }
}

interface EventEmitter {
    fun <T> Event<T>.emit(data: T) {
        emit(data)
    }
}






