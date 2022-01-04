package com.bosha.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import androidx.annotation.MainThread
import com.bosha.utils.databinding.ViewAwesomeCheckboxBinding

class AwesomeCheckbox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : FrameLayout(context, attrs, defaultAttrs) {


    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ViewAwesomeCheckboxBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    var checked
        @MainThread
        set(value) {
            isChecked = value
            setUpIconVisibility()
        }
        get() = isChecked

    var animationType: Animation? = null

    init {
        binding.ivFavorite.setOnClickListener { check() }
    }

    private fun setUpIconVisibility() {
        if (animationType?.ordinal == Animation.SCALE.ordinal) {
            val scaleValue = if (checked) 1f else 0f
            binding.ivFavorite.scaleY = scaleValue
            binding.ivFavorite.scaleX = scaleValue
        } else {
            binding.ivFavorite.alpha = if (checked) 1f else 0f
        }
    }

    private var onCheckChange: ((Boolean) -> Unit)? = null

    private var isChecked = false

    @MainThread
    private fun check() {
        animateCheck()
        isChecked = checked.not()
        onCheckChange?.invoke(checked)
    }

    private fun animateCheck() {
        if (checked) {
            binding.ivFavorite.animate().apply {
                when (animationType) {
                    Animation.SCALE -> {
                        scaleX(0f)
                        scaleY(0f)
                        duration = 1000L
                        interpolator = AnticipateInterpolator()
                    }
                    Animation.FADE -> {
                        alpha(0f)
                        duration = 1000L
                    }
                    else -> {
                        alpha(0f)
                        duration = 0
                    }
                }
                start()
            }
        } else {
            binding.ivFavorite.animate().apply {
                when (animationType) {
                    Animation.SCALE -> {
                        scaleX(1f)
                        scaleY(1f)
                        interpolator = AnticipateOvershootInterpolator()
                        duration = 1000L
                    }
                    Animation.FADE -> {
                        alpha(1f)
                        duration = 1000L
                    }
                    else -> {
                        alpha(1f)
                        duration = 0
                    }
                }
                start()
            }
        }
    }

    fun onCheckChange(action: (Boolean) -> Unit) {
        onCheckChange = action
    }

    enum class Animation {
        SCALE,
        FADE,
        NOPE
    }
}