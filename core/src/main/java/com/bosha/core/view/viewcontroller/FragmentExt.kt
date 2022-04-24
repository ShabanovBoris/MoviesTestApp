package com.bosha.core.view.viewcontroller

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


inline fun <reified B : ViewBinding, reified V : ViewModel> Fragment.screen(): ScreenControllerDelegate<B, V> {
    return ScreenControllerDelegate(V::class, B::class)
}

class ScreenControllerDelegate<B : ViewBinding, V : ViewModel>(
    private val viewModelType: KClass<V>,
    private val bindingType: KClass<B>
) : ReadOnlyProperty<Fragment, ScreenController<B, V>> {

    private var value: ScreenController<B, V>? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): ScreenController<B, V> {
        return value ?: ScreenControllerDefaultImpl.create(
            viewModelType,
            bindingType
        ) { thisRef }.also {
            it.lifecycleDelegate(thisRef)
            //Change the lifecycle to viewLifecycle when it be ready
            it.onPreDraw {
                it.lifecycleDelegate(thisRef.viewLifecycleOwner)
            }
            value = it
        }
    }
}

inline fun <reified B : ViewBinding> Fragment.screenWithoutViewModel(): ScreenControllerDelegate<B, Nothing> {
    return ScreenControllerDelegate(Nothing::class, B::class)
}