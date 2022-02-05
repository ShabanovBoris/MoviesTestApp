package com.bosha.core.view.viewcontroller

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding


//make delegate
@Suppress("FunctionName")
inline fun <reified B : ViewBinding, reified V : ViewModel> Fragment.Screen(): ViewController<B, V> {
    val screen = ViewControllerDefaultImpl.get(V::class, B::class) { this }
    screen.lifecycleDelegate(this)
    //Change the lifecycle to viewLifecycle when it be ready
    screen.onPreDraw {
        screen.lifecycleDelegate(viewLifecycleOwner)
    }
    return screen
}

@Suppress("FunctionName")
/**
 *  Without view model
 */
inline fun <reified B : ViewBinding> Fragment.ScreenView(): ViewController<B, Nothing> {
    val screen = ViewControllerDefaultImpl.get(Nothing::class, B::class) { this }
    screen.lifecycleDelegate(this)
    //Change the lifecycle to viewLifecycle when it be ready
    screen.onPreDraw {
        screen.lifecycleDelegate(viewLifecycleOwner)
    }
    return screen
}