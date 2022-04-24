package com.bosha.core.navigation

object NavConstants {
    internal val SCHEME = "moviesdemo://"
    val MAIN_BACKSTACK = "mainbackstackkey"
}

enum class Screens(val value: String) {
    MAIN("main"),
    DETAIL("detail"),
    WEB_VIEW("webview"),
    ERROR("error"),
    SEARCH("search");

    override fun toString(): String {
        return value
    }
}