package com.jay.currencyconverter.util.ui

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class LayoutParamsAnimator : Animation() {

    var view: View? = null
    var targetHeight: Int = 0
    private var initialHeight: Int = 0

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        view ?: throw NullPointerException("view must be set")

        view?.let {
            initialHeight = it.layoutParams.height

            it.layoutParams.height =
                initialHeight.plus((targetHeight.minus(initialHeight) * interpolatedTime)).toInt()

            it.requestLayout()
        }
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean = true
}