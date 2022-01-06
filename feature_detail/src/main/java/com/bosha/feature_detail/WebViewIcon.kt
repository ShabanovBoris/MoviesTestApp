package com.bosha.feature_detail

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.bosha.feature_detail.databinding.WebViewCustomBinding

class WebViewIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defaultAttr: Int = 0
) : LinearLayout(context, attrs, defaultAttr) {

    private var binding: WebViewCustomBinding? = null
    private var color: Int? = null

    init {
        orientation = VERTICAL
        context.withStyledAttributes(attrs, R.styleable.WebViewIcon, defaultAttr, 0) {
            val defaultColor = TypedValue()
            context.theme.resolveAttribute(R.attr.colorOnSecondary, defaultColor, true)
            color = getColor(R.styleable.WebViewIcon_android_tint, defaultColor.data)
        }
        initIcon()
    }

    private fun initIcon() = WebViewCustomBinding.inflate(LayoutInflater.from(context), this).also {
        val color = color ?: return@also
        it.ivIconWeb.drawable.setTint(color)
        it.tvWebView.setTextColor(color)
    }
}