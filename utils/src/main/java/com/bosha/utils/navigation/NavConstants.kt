package com.bosha.utils.navigation

object NavConstants {
    val SCHEME = "moviesdemo://"
}

enum class Screens(val value: String) {
    MAIN("main"),
    DETAIL("detail"),
    SEARCH("search");

    override fun toString(): String {
        return value
    }
}