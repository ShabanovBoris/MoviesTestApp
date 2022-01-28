package com.bosha.uikit

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.bosha.uikit.databinding.WebViewCustomBinding

class WebViewIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defaultAttr: Int = 0
) : LinearLayout(context, attrs, defaultAttr) {

    private val binding by lazy(LazyThreadSafetyMode.NONE){
        WebViewCustomBinding.inflate(LayoutInflater.from(context), this)
    }
    private var color: Int? = null

    init {
        orientation = VERTICAL
        context.withStyledAttributes(attrs, R.styleable.WebViewIcon, defaultAttr, 0) {
            val defaultColor = TypedValue()
            context.theme.resolveAttribute(R.attr.colorOnSecondary, defaultColor, true)
            color = getColor(R.styleable.WebViewIcon_android_tint, defaultColor.data)
        }
        initIconColor()
    }

    private fun initIconColor() {
        color?.let {
            binding.ivIconWeb.drawable.setTint(it)
            binding.tvWebView.setTextColor(it)
        }
    }
}