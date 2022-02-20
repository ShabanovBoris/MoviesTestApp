package com.bosha.core.view

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.bosha.core.navigation.NavCommand
import com.bosha.core.navigation.Screens
import com.bosha.core.navigation.navigate
import com.bosha.core.view.viewcontroller.ScreenController
import com.bosha.core.view.viewcontroller.screenWithoutViewModel
import com.bosha.uikit.databinding.FragmentCommonErrorBinding

class FragmentErrorScreen : BaseFragment<FragmentCommonErrorBinding, Nothing>() {
    override val screen: ScreenController<FragmentCommonErrorBinding, Nothing> by screenWithoutViewModel()

    private val config: ErrorConfig by lazy {
        val image = requireNotNull(requireArguments().getString("imageRes")).toInt()
        val text = requireNotNull(requireArguments().getString("textRes")).toInt()
        val description = requireNotNull(requireArguments().getString("descriptionRes")).toInt()
        ErrorConfig(text, description, image)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        screen.views {
            errorTvText.setText(config.textRes)
            errorTvDescription.setText(config.descriptionRes)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigate {
                target = NavCommand(Screens.MAIN)
                options {
                    popUpTo(getNavGraphHost())
                }
            }
        }
    }


}