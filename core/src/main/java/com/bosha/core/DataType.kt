package com.bosha.core

sealed interface DataType<out T> {
    object Empty : DataType<Nothing>

    data class Data<out T>(val data: T) : DataType<T>

    data class Error(val error: Throwable) : DataType<Nothing>

    object Default : DataType<Nothing>

    object Loading : DataType<Nothing>

    companion object {
        fun empty() = Empty
        fun <T> data(data: T) = Data(data)
        fun loading() = Loading
        fun default() = Default
        fun error(error: Throwable) = Error(error)
    }
}

