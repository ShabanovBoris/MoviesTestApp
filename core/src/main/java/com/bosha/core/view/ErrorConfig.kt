package com.bosha.core.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bosha.core.R

data class ErrorConfig(
    @StringRes val textRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
) {
    companion object {
        val commonErrorConfig: ErrorConfig =
            ErrorConfig(
                R.string.default_error_text,
                R.string.default_error_description,
                R.drawable.ic_default_error
            )
    }
}

fun ScreenViewModel.showError(errorConfig: ErrorConfig = ErrorConfig.commonErrorConfig) {
    errorEvent.emit(errorConfig)
}