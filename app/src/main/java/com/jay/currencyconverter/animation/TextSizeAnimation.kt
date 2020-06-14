package com.jay.currencyconverter.animation

import android.animation.ValueAnimator
import android.content.Context
import android.widget.TextView
import com.jay.currencyconverter.BaseApplication


object TextSizeAnimation {

    val context: Context = BaseApplication.baseComponent.application.baseContext

    fun TextView.getTextSizeInSP(): Float{
        val px: Float = this.textSize
        return px / context.resources.displayMetrics.scaledDensity
    }

    fun TextView.setTextSizeWithAnimation(targetSize: Float, duration: Long = 200) {
        val startSize: Float = this.getTextSizeInSP()
        val animator: ValueAnimator = ValueAnimator.ofFloat(startSize, targetSize)
        animator.duration = duration

        animator.addUpdateListener { valueAnimator ->
            val animatedValue: Float = valueAnimator.animatedValue as Float
            this.textSize = animatedValue
        }
        animator.start()
    }
}