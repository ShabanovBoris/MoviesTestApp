package com.bosha.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.bosha.core.view.viewcontroller.ViewController

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>: Fragment() {

    abstract val screen: ViewController<VB, VM>

    @CallSuper // todo нужен ли
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = screen {
        inflateView(inflater, container)
    }
}