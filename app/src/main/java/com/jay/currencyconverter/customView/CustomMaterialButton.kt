package com.jay.currencyconverter.customView

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R

class CustomMaterialButton : MaterialButton {

    var isPressedState = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

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
    }

    fun enable() {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
        isClickable = true
    }
}