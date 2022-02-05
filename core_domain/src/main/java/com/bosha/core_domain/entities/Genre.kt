package com.bosha.core_domain.entities

/**
 * A class that warps primitive loses a lot of performance,
 * but primitives in jvm already optimize.
 * The key word [value] says to convert class to primitive type in jvm
 */
@JvmInline
value class Genre(val name: String){
    init {
        require(name.isNotEmpty())
    }
}