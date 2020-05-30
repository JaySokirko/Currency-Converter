package com.jay.currencyconverter.customView

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class CustomMaterialButton : MaterialButton {

    var isButtonPressed = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)
}