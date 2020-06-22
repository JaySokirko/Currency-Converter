package com.jay.currencyconverter.ui.adapter.viewHolder

import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

abstract class AnimatedViewHolder<T>(itemView: View) : BaseViewHolder<T>(itemView) {

    protected abstract val rootView: View
    protected var animation: Int = android.R.anim.slide_in_left
    private val alreadyAnimated = true

    fun clearAnimation() {
        rootView.clearAnimation()
    }

    open fun setAnimation(uniqueId: String? = null) {
        if (viewHolderIds[uniqueId] == alreadyAnimated) return

        uniqueId?.let { viewHolderIds.put(it, alreadyAnimated) }

        itemView.visibility = View.INVISIBLE

        val delay: Long = when {
            layoutPosition == 0 -> 100L
            layoutPosition > 8 -> 100L
            else -> layoutPosition * 100L + 100L
        }

        Handler().postDelayed(
            {
                itemView.visibility = View.VISIBLE

                val animation: Animation =
                    AnimationUtils.loadAnimation(applicationContext, animation)
                itemView.startAnimation(animation)

            }, delay)
    }

    companion object {
        private var viewHolderIds: MutableMap<String, Boolean> = mutableMapOf()

        fun clearViewHolderIds() {
            viewHolderIds.clear()
        }
    }

}