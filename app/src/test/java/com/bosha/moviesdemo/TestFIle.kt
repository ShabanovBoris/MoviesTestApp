package com.bosha.moviesdemo

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlin.system.measureTimeMillis


fun main(){ runBlocking {
    val stateFlow = MutableStateFlow<Int?>(null)

    launch {

        for (it in 1..60){

            delay(1000)
            stateFlow.value = it
        }

    }

    launch {
        throw CancellationException()
        withTimeout(9000L){
            stateFlow.filterNotNull()
                .collect {
//                    ensureActive()
                    println(it)
                }
        }
        measureTimeMillis {

        }
    }

}
//    (1..99).toList().forEach {
//        println(it)
//        if (it / 37 == 1) return@forEach
//    }
//    for (it in 1..99){
//        println(it)
//        if (it / 37 == 1) break
//    }
//



//    CoroutineScope()
//    coroutineScope {  }
//    (1..99).find { it == 37 }
}

