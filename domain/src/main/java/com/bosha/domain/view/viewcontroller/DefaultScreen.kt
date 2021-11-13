package com.bosha.domain.view.viewcontroller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.bosha.domain.view.ViewLifecycleDelegate
import kotlinx.coroutines.CoroutineScope
import kotlin.properties.Delegates
import kotlin.reflect.KClass

class DefaultScreen<B : ViewBinding, V : ViewModel> private constructor() :
    ViewController<B, V> {

    val lifecycleDelegate = ViewLifecycleDelegate(
        doOnDestroyView = {
            destroyView()
        }
    )

    private val doOnPreDraw = mutableSetOf<() -> Unit>()

    private var viewModelType: KClass<V> by Delegates.notNull()

    private var viewBindingType: KClass<B> by Delegates.notNull()

    private var vmStoreInitializer: (() -> ViewModelStoreOwner)? = null

    private val viewModel by lazy {
        val vmStoreInit =
            requireNotNull(vmStoreInitializer) { "for using view model, you have to pass initializer" }
        ViewModelProvider(vmStoreInit()).get(viewModelType.java)
    }

    private var _binding: B? = null
    private val binding get() = checkNotNull(_binding) { "call createView() before use binding" }

    override fun destroyView() {
        _binding = null
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        _binding = viewBindingType.members.filter { it.name == "inflate" }[1]
            .call(inflater, container, false) as B

        binding.root.doOnPreDraw {
            doOnPreDraw.forEach { it() }
        }

        return binding.root
    }

    override fun onPreDraw(action: () -> Unit) {
        doOnPreDraw.add(action)
    }

    override operator fun invoke(block: ViewController<B, V>.() -> View): View = block()

    override fun view(block: B.() -> Unit) {
        binding.block()
    }

    override fun viewModel(block: V.() -> Unit) {
        viewModel.block()
    }

    override fun viewModelInScope(block: CoroutineScope.(V) -> Unit) {
        lifecycleDelegate.doInLifecycleScope {
            block(viewModel)
        }
    }

    companion object {
        @MainThread
        fun <B : ViewBinding, V : ViewModel> get(
            viewModelType: KClass<V>,
            viewBindingType: KClass<B>,
            vmStoreInitializer: () -> ViewModelStoreOwner
        ) = DefaultScreen<B, V>().also {

            it.vmStoreInitializer = vmStoreInitializer

            it.viewModelType = viewModelType

            it.viewBindingType = viewBindingType
        }
    }
}
