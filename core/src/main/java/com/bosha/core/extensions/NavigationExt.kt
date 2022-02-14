package com.bosha.core.extensions

import androidx.transition.Transition
import com.google.android.material.transition.MaterialContainerTransform

fun MaterialContainerTransform.doOnEndTransition(action: () -> Unit){
    addListener(object : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) {}
        override fun onTransitionCancel(transition: Transition){}
        override fun onTransitionPause(transition: Transition) {}
        override fun onTransitionResume(transition: Transition) {}
        override fun onTransitionEnd(transition: Transition) {
            action()
        }
    })
}