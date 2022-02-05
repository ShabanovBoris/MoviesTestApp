package com.bosha.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Класс для создания наблюдаемого состояния
 */
class DataState<T>(initial: DataType<T> = DataType.Empty) {
    private val stateFlow = MutableStateFlow(initial)

    val flow get() = stateFlow.asStateFlow()

    internal fun set(value: DataType<T>) {
        stateFlow.tryEmit(value)
    }
}

fun <T> Fragment.observe(
    dataState: DataState<T>,
    onError: (Throwable) -> Unit = {},
    onDefault: () -> Unit = {},
    onEmpty: () -> Unit = {},
    onLoading: () -> Unit = {},
    onReady: (T) -> Unit,
) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            dataState.flow.collect {
                when (it) {
                    is DataType.Data -> onReady(it.data)
                    DataType.Default -> onDefault()
                    DataType.Empty -> onEmpty()
                    is DataType.Error -> onError(it.error)
                    DataType.Loading -> onLoading()
                }
            }
        }
    }
}

interface DataStateEmitter {
    fun <T> DataState<T>.emit(value: DataType<T>) {
        set(value)
    }

    fun <T> DataState<T>.update(assign: () -> (DataType<T>)) {
        set(assign())
    }
}
