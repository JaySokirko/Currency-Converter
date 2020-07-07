package com.jay.currencyconverter.ui.adapter.viewHolder

import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.jay.currencyconverter.R

abstract class AnimatedViewHolder<T>(itemView: View) : BaseViewHolder<T>(itemView) {

    protected abstract val rootView: View
    protected var scrollToBottomAnimation: Int = R.anim.fall_down_animation
    protected var scrollToTopAnimation: Int = R.anim.fall_up_animation

    fun clearAnimation() {
        rootView.clearAnimation()
    }

    fun animateScrollToBottom() {
        val layoutAnimationController: LayoutAnimationController? =
            AnimationUtils.loadLayoutAnimation(applicationContext, scrollToBottomAnimation)

        itemView.animation = layoutAnimationController?.animation
    }

    fun animateScrollToTop() {
        val layoutAnimationController: LayoutAnimationController? =
            AnimationUtils.loadLayoutAnimation(applicationContext, scrollToTopAnimation)

        itemView.animation = layoutAnimationController?.animation
    }
}