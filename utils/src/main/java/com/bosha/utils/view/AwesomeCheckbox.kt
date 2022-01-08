package com.bosha.utils.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getIntOrThrow
import androidx.core.content.withStyledAttributes
import com.bosha.utils.R
import com.bosha.utils.databinding.ViewAwesomeCheckboxBinding

@RequiresApi(Build.VERSION_CODES.Q)
class AwesomeCheckbox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = R.style.Widget_Movies_AwesomeCheckbox
) : ConstraintLayout(context, attrs, defStyleAttrs, defStyleRes) {


    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ViewAwesomeCheckboxBinding.inflate(
            LayoutInflater.from(context),
            this
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
    var iconsTint: Int? = null

    init {
        binding.ivFavorite.setOnClickListener { check() }
        context.withStyledAttributes(
            attrs,
            R.styleable.AwesomeCheckbox,
            defStyleAttrs,
            defStyleRes
        ) {
            getIntOrThrow(R.styleable.AwesomeCheckbox_animationType).let {
                animationType = Animation.values()[it]
            }
//            val defaultColor = TypedValue()
//            context.theme.resolveAttribute(R.attr.colorSecondary, defaultColor, true)
//            iconsTint = getColor(R.styleable.AwesomeCheckbox_android_tint, defaultColor.data)
            iconsTint = getColorOrThrow(R.styleable.AwesomeCheckbox_android_tint)
        }

        iconsTint?.let {
            binding.ivFavorite.drawable.setTint(it)
            binding.ivFavoriteBg.drawable.setTint(it)
        }
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

    private var onCheckChange: MutableSet<(Boolean) -> Unit> = mutableSetOf()

    private var isChecked = false

    @MainThread
    private fun check() {
        animateCheck()
        isChecked = checked.not()
        onCheckChange.forEach{
            it(checked)
        }
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
        onCheckChange.add(action)
    }

    enum class Animation(val value: Int) {
        SCALE(0),
        FADE(1),
        NOPE(2)
    }
}