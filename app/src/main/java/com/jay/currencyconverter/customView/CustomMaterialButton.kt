package com.jay.currencyconverter.customView

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R

class CustomMaterialButton : MaterialButton {

    var isPressedState = false
    var isEnable = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    fun setPressedState() {
        isPressedState = true
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorAccent)
    }

    fun setNotPressedState() {
        isPressedState = false
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
    }

    fun disable() {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.gray_300)
        isClickable = false
        isEnable = false
    }

    fun enable() {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
        isClickable = true
        isEnable =  true
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val styledAttrs: TypedArray? =
            context.obtainStyledAttributes(attrs, R.styleable.CustomMaterialButton)

        val isEnabledByDefault: Boolean? =
            styledAttrs?.getBoolean(R.styleable.CustomMaterialButton_isEnabled, true)

        isEnabledByDefault?.let { if (!it) disable() }

        styledAttrs?.recycle()
    }
}