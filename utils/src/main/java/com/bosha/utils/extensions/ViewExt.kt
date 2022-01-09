package com.bosha.utils.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.updateLayoutParams

fun View.setPaddingTop(padding: Int) {
    setPadding(
        paddingLeft,
        padding,
        paddingRight,
        paddingBottom
    )
}

fun View.setMarginTop(margin: Int) {
   updateLayoutParams<ViewGroup.MarginLayoutParams> {
        setMargins(
           marginLeft,
            margin,
           marginRight,
           marginBottom,
        )
    }
}