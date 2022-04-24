package com.bosha.core

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Класс для создания наблюдаемого состояния
 */
class DataState<T>(initial: TypedState<T> = TypedState.Empty) {
    private val stateFlow = MutableStateFlow(initial)

    val flow get() = stateFlow.asStateFlow()

    internal fun setValue(value: TypedState<T>) {
        stateFlow.tryEmit(value)
    }

    internal fun safeUpdate(value: TypedState<T>) {
        //compareAndSet
        stateFlow.update { value }
    }
}

fun <T> Fragment.observeInScope(
    dataState: DataState<T>,
    onError: (Throwable) -> Unit = {},
    onDefault: () -> Unit = {},
    onEmpty: () -> Unit = {},
    onLoading: () -> Unit = {},
    onReady: (T) -> Unit = {},
) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            dataState.flow.collect {
                when (it) {
                    is TypedState.Data -> onReady(it.data)
                    TypedState.Default -> onDefault()
                    TypedState.Empty -> onEmpty()
                    is TypedState.Error -> onError(it.error)
                    TypedState.Loading -> onLoading()
                }
            }
        }
    }
}

interface DataStateEmitter {
    @MainThread
    fun <T> DataState<T>.emit(value: TypedState<T>) {
        setValue(value)
    }

    @MainThread
    fun <T> DataState<T>.emitData(value: T) {
        setValue(TypedState.data(value))
    }

    @WorkerThread
    fun <T> DataState<T>.safeUpdate(promiseValue: () -> (TypedState<T>)) {
        safeUpdate(promiseValue())
    }
}
