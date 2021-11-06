package com.bosha.domain.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

inline fun <reified B : ViewBinding, reified V : ViewModel> Fragment.createScreen(): ViewController<B, V> {

    val screen = DefaultScreen.get(V::class, B::class) { this }

    screen.afterViewInflated {
        screen.lifecycleDelegate(viewLifecycleOwner)
    }

    return screen
}

/**
 *  Without view model
 */
inline fun <reified B : ViewBinding> Fragment.createScreenView(): ViewController<B, Nothing> {

    val screen = DefaultScreen.get(Nothing::class, B::class) { this }

    screen.afterViewInflated {
        screen.lifecycleDelegate(viewLifecycleOwner)
    }

    return screen
}
