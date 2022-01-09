package com.bosha.utils.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
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

fun <T : Fragment> T.hideSystemBars(rootView: View) {
    val controller = WindowCompat.getInsetsController(requireActivity().window, rootView)
    controller?.hide(WindowInsetsCompat.Type.navigationBars())
    controller?.hide(WindowInsetsCompat.Type.statusBars())
}

fun <T : Fragment> T.showSystemBars(rootView: View) {
    val controller = WindowCompat.getInsetsController(requireActivity().window, rootView)
    controller?.show(WindowInsetsCompat.Type.navigationBars())
    controller?.show(WindowInsetsCompat.Type.statusBars())
}

fun <T : Fragment> T.hideStatusBar(rootView: View) {
    val controller = WindowCompat.getInsetsController(requireActivity().window, rootView)
    controller?.hide(WindowInsetsCompat.Type.statusBars())
}

fun <T : Fragment> T.applyInsetsFitsSystemWindows(
    rootView: View,
    handleInset: (WindowInsetsCompat) -> WindowInsetsCompat
) {
    fitsSystemWindows(false)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, insets ->
        handleInset(insets)
    }
}

// tell the window that we want to handle/fit any system windows
fun <T : Fragment> T.fitsSystemWindows(shouldFits: Boolean): Unit =
    WindowCompat.setDecorFitsSystemWindows(requireActivity().window, shouldFits)

