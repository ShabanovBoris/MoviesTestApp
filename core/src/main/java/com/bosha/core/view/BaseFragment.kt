package com.bosha.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.bosha.core.navigation.NavCommand
import com.bosha.core.navigation.Screens
import com.bosha.core.navigation.navigate
import com.bosha.core.observeEvent
import com.bosha.core.view.viewcontroller.ScreenController
import kotlinx.coroutines.CoroutineScope
import logcat.LogPriority
import logcat.logcat

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    abstract val screen: ScreenController<VB, VM>
    val binding get() = screen.binding
    val viewModel get() = screen.viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = screen {
        onPreDraw {
            logcat(
                priority = LogPriority.INFO,
                OPEN_SCREEN_LOG_TAG
            ) { this@BaseFragment::class.java.name }
        }
        inflateView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeEvent(viewModel.errorEvent) {
            navigate {
                target = NavCommand(Screens.ERROR).setArgs(
                    it.textRes.toString(),
                    it.descriptionRes.toString(),
                    it.imageRes.toString()
                )
                options {

                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logcat(
            priority = LogPriority.INFO,
            CLOSE_SCREEN_LOG_TAG
        ) { this@BaseFragment::class.java.name }
    }

    fun doInScope(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    companion object {
        private const val OPEN_SCREEN_LOG_TAG = "OPEN_SCREEN_LOG_TAG"
        private const val CLOSE_SCREEN_LOG_TAG = "CLOSE_SCREEN_LOG_TAG"
    }
}