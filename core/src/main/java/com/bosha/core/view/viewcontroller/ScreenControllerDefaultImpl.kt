package com.bosha.core.view.viewcontroller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.bosha.core.extensions.inflateViewBinding
import com.bosha.core.view.ViewLifecycleDelegate
import logcat.logcat
import kotlin.properties.Delegates
import kotlin.reflect.KClass

class ScreenControllerDefaultImpl<B : ViewBinding, V : ViewModel> private constructor() :
    ScreenController<B, V> {

    private var _binding: B? = null
    override val binding get() = checkNotNull(_binding) { "binding is null" }

    private var viewModelType: KClass<V> by Delegates.notNull()

    private var viewBindingType: KClass<B> by Delegates.notNull()

    private var vmStoreInitializer: (() -> ViewModelStoreOwner)? = null

    private val doOnPreDraw = mutableSetOf<() -> Unit>()

    override val viewModel by lazy {
        val vmStoreInit =
            requireNotNull(vmStoreInitializer) { "for using view model, you have to pass initializer" }
        ViewModelProvider(vmStoreInit()).get(viewModelType.java)
    }

    val lifecycleDelegate = ViewLifecycleDelegate(
        doOnDestroyView = {
            destroyView()
        }
    )

    override operator fun invoke(block: ScreenController<B, V>.() -> View): View = block()

    override fun destroyView() {
        _binding = null
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        _binding = viewBindingType.inflateViewBinding(inflater, container)

        binding.root.doOnPreDraw {
            doOnPreDraw.forEach { it() }
            doOnPreDraw.clear()
        }

        return binding.root
    }

    /**
     * DSL [onPreDraw] [views] [viewModel]
     */
    override fun onPreDraw(action: () -> Unit) {
        val wrapper = {
            action()
            logcat { "onPreDraw size ${doOnPreDraw.size}" }
        }
        doOnPreDraw.add(wrapper)
    }

    override fun views(block: B.() -> Unit) {
        binding.block()
    }

    override fun viewModel(block: V.() -> Unit) {
        viewModel.block()
    }

    companion object {
        @MainThread
        fun <B : ViewBinding, V : ViewModel> create(
            viewModelType: KClass<V>,
            viewBindingType: KClass<B>,
            vmStoreInitializer: () -> ViewModelStoreOwner
        ) = ScreenControllerDefaultImpl<B, V>().also {

            it.vmStoreInitializer = vmStoreInitializer

            it.viewModelType = viewModelType

            it.viewBindingType = viewBindingType
        }
    }
}
