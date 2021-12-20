package com.bosha.feature_detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bosha.feature_detail.databinding.WebViewCustomBinding

class WebViewIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    default: Int = 0
): LinearLayout(context, attrs, default) {

    init {
        orientation = VERTICAL
    }

    fun initIcon() = WebViewCustomBinding.inflate(LayoutInflater.from(context), this).also {
        it.ivIconWeb.drawable.setTint(context.resources.getColor(R.color.design_default_color_error, context.theme))
    }
}