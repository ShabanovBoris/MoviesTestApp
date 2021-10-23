package com.bosha.utils.extensions

import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

fun <T : Fragment> T.waitPreDraw() {
    /**
     * Waiting for the view, that must be ready for draw
     */
    postponeEnterTransition()
    view?.doOnPreDraw {
        startPostponedEnterTransition()
    }
}


fun <T : Fragment> T.onViewLifecycleWhenStarted(block: suspend () -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }