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
import com.bosha.core.view.ViewLifecycleDelegate
import com.bosha.utils.extensions.inflateView
import kotlinx.coroutines.CoroutineScope
import logcat.logcat
import kotlin.properties.Delegates
import kotlin.reflect.KClass

class ViewControllerDefaultImpl<B : ViewBinding, V : ViewModel> private constructor() :
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
    override val binding get() = checkNotNull(_binding) { "binding is null" }

    override fun destroyView() {
        _binding = null
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        _binding = viewBindingType.inflateView(inflater, container)

        binding.root.doOnPreDraw {
            doOnPreDraw.forEach { it() }
            doOnPreDraw.clear()
        }

        return binding.root
    }

    override fun onPreDraw(action: () -> Unit) {
        val wrapper = {
            action()
            logcat { "onPreDraw size ${doOnPreDraw.size}" }
        }
        doOnPreDraw.add(wrapper)
    }

    override operator fun invoke(block: ViewController<B, V>.() -> View): View = block()

    override fun views(block: B.() -> Unit) {
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
        ) = ViewControllerDefaultImpl<B, V>().also {

            it.vmStoreInitializer = vmStoreInitializer

            it.viewModelType = viewModelType

            it.viewBindingType = viewBindingType
        }
    }
}
