package com.bosha.moviesdemo.ui

import android.content.Context
import android.view.Choreographer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bosha.moviesdemo.BuildConfig
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

@ActivityScoped
class FpsMeter @Inject constructor() {
    private var lastTimeStamp = 0L

    private val _flow = MutableSharedFlow<Int>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val flow get() = _flow.distinctUntilChanged()

    var callback: Choreographer.FrameCallback? = null

    fun start() {
        createCallback()
        Choreographer.getInstance().postFrameCallback(callback)
        println("${this::class.java.simpleName} STARTED")
    }

    fun stop() {
        Choreographer.getInstance().removeFrameCallback(callback)
        callback = null
        println("${this::class.java.simpleName} STOPPED")
    }

    init {
        if (BuildConfig.DEBUG) {
            createCallback()
        }
    }

    private fun createCallback() {
        callback = Choreographer.FrameCallback { frameTimeNanos ->
            try {
                val frameTime = 1000 / ((frameTimeNanos - lastTimeStamp) / 1000000)
                _flow.tryEmit(frameTime.toInt())
            } catch (ae: ArithmeticException) {
                logcat(LogPriority.ERROR) { ae.toString() }
            }
            lastTimeStamp = frameTimeNanos
            Choreographer.getInstance().postFrameCallback(callback)
        }
    }
}

fun FpsMeter.observeFps(lifecycleOwner: LifecycleOwner, onUpdate: (Int) -> Unit) {
    if (BuildConfig.DEBUG.not()) return
    start()
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            flow.collect { onUpdate(it) }
        }
    }.invokeOnCompletion { e ->
        if (e != null && e is CancellationException) {
            stop()
        }
    }
}
