package com.bosha.core

sealed interface TypedState<out T> {
    object Empty : TypedState<Nothing>

    data class Data<out T>(val data: T) : TypedState<T>

    data class Error(val error: Throwable) : TypedState<Nothing>

    object Default : TypedState<Nothing>

    object Loading : TypedState<Nothing>

    companion object {
        fun empty() = Empty
        fun <T> data(data: T) = Data(data)
        fun loading() = Loading
        fun default() = Default
        fun error(error: Throwable) = Error(error)
    }
}

