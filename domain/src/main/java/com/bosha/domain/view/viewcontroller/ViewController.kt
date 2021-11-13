package com.bosha.domain.view.viewcontroller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope

interface ViewController<B : ViewBinding, V : ViewModel> {

    fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View

    fun destroyView()

    /**
     * The view already inflated but not showed yet
     */
    fun onPreDraw(action: () -> Unit)

    operator fun invoke(block: ViewController<B, V>.() -> View): View

    @MainThread
    fun view(block: B.() -> Unit)

    fun viewModel(block: V.() -> Unit)

    fun viewModelInScope(block: CoroutineScope.(V) -> Unit)
}