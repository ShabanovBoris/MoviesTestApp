package com.bosha.core.navigation

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

