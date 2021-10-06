package com.bosha.utils.extensions

import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment

fun Fragment.waitPreDraw() {
    /**
     * Waiting for the view, that must be ready for draw
     */
    postponeEnterTransition()
    view?.doOnPreDraw {
        startPostponedEnterTransition()
    }
}