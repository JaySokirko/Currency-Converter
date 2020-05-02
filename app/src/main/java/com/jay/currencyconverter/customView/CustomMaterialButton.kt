package com.jay.currencyconverter.customView

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R
import com.jay.currencyconverter.util.ViewState

class CustomMaterialButton : MaterialButton {

    var state: ViewState.ButtonPress = ViewState.ButtonPress.NO_PRESSED

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun disable() {
        isClickable = false
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.gray_300)
    }

    fun enable() {
        isClickable = true
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
    }
}