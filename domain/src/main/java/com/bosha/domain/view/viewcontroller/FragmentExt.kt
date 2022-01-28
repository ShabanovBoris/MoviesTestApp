package com.bosha.domain.view.viewcontroller

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

inline fun <reified B : ViewBinding, reified V : ViewModel> Fragment.createScreen(): ViewController<B, V> {

    val screen = ViewControllerDefaultImpl.get(V::class, B::class) { this }


    screen.lifecycleDelegate(this)

    /**
     * Change the lifecycle to viewLifecycle when it be ready
     */
    screen.onPreDraw {
            screen.lifecycleDelegate(viewLifecycleOwner)
        }


    return screen
}

/**
 *  Without view model
 */
inline fun <reified B : ViewBinding> Fragment.createScreenView(): ViewController<B, Nothing> {

    val screen = ViewControllerDefaultImpl.get(Nothing::class, B::class) { this }

    screen.lifecycleDelegate(this)

    /**
     * Change the lifecycle to viewLifecycle when it be ready
     */
    screen.onPreDraw {
        screen.lifecycleDelegate(viewLifecycleOwner)
    }


    return screen
}
