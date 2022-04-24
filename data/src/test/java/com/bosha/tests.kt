package com.bosha

import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class DelegateA : ReadOnlyProperty<Nothing?, String> {
    init {
        Thread.sleep(99)

        println(this::class.java.hashCode().toString() + " initialized " + Date().time.toString())
    }

    val value: String = Date().time.toString()
    override fun getValue(thisRef: Nothing?, property: KProperty<*>): String {
        return value
    }
}

val a by DelegateA()
val b by DelegateA()
val c by DelegateA()

fun main(){
    println(a)
    Thread.sleep(99)
    println(a)
    Thread.sleep(99)
    println(b)
    Thread.sleep(99)
    println(c)
}
