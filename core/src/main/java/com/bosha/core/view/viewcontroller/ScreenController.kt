package com.bosha.core.view.viewcontroller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

interface ScreenController<B : ViewBinding, V : ViewModel> {

    /**
     * Open property for work with binding in outside
     */
    val binding: B

    /**
     * Open property for work with binding in outside
     */
    val viewModel: V

    /**
     * Dsl for work with binding
     */
    @MainThread
    fun views(block: B.() -> Unit)

    /**
     * Inflating view binding
     */
    fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View

    /**
     * Destroy view binding
     */
    fun destroyView()

    /**
     * The view already inflated but not showed yet
     */
    fun onPreDraw(action: () -> Unit)

    /**
     *  screen {
     *      views { }
     *      viewModel { }
     *   }
     */
    operator fun invoke(block: ScreenController<B, V>.() -> View): View

    /**
     * Access to viewmodel
     */
    fun viewModel(block: V.() -> Unit)
}