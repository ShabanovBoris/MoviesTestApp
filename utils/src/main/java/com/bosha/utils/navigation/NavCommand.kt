package com.bosha.utils.navigation

import android.os.Bundle
import androidx.navigation.NavOptions

data class NavCommand(
    val screen: Screens,
    val description: () -> String = {""},
){
    var url: String = screen.toString()

    fun setArgs(vararg args:String): NavCommand {
        args.forEach {
            url = "$url/$it"
        }
        return this
    }
}

