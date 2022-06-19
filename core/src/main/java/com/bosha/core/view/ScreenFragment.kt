package com.bosha.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bosha.core.extensions.printHierarchyTrace
import com.bosha.core.navigation.NavCommand
import com.bosha.core.navigation.Screens
import com.bosha.core.navigation.navigate
import com.bosha.core.observeEvent
import com.bosha.core.view.viewcontroller.ScreenController
import logcat.LogPriority
import logcat.logcat

abstract class ScreenFragment<VB : ViewBinding, VM : ScreenViewModel> : Fragment() {

    abstract val screen: ScreenController<VB, VM>
    val binding get() = screen.binding
    val viewModel get() = screen.viewModel

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = screen {
        onPreDraw {
            logcat(
                priority = LogPriority.INFO,
                OPEN_SCREEN_LOG_TAG
            ) { this@ScreenFragment::class.java.name }
        }
        inflateView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        printHierarchyTrace()
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
        ) { this@ScreenFragment::class.java.name }
    }

    companion object {
        private const val OPEN_SCREEN_LOG_TAG = "OPEN_SCREEN_LOG_TAG"
        private const val CLOSE_SCREEN_LOG_TAG = "CLOSE_SCREEN_LOG_TAG"
    }
}