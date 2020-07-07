package com.jay.currencyconverter.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.ui.adapter.viewHolder.AnimatedViewHolder
import com.jay.currencyconverter.util.ui.SmoothScroller

abstract class AnimatedRecyclerAdapter<T> : RecyclerView.Adapter<AnimatedViewHolder<T>>() {

    protected var recyclerView: RecyclerView? = null
    protected var holder: AnimatedViewHolder<T>? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: AnimatedViewHolder<T>) {
        this.holder = holder
        holder.animateScrollToBottom()
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: AnimatedViewHolder<T>) {
        holder.clearAnimation()
        super.onViewDetachedFromWindow(holder)
    }

    protected fun smoothScrollToTop() {
        SmoothScroller.getSmoothScroller().targetPosition = 0
        recyclerView?.layoutManager?.startSmoothScroll(SmoothScroller.getSmoothScroller())
    }

    protected fun setScrollChangeListener(
        onScrollToTop: (() -> Unit)? = null,
        onScrollToBottom: (() -> Unit)? = null
    ) {

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    onScrollToBottom?.let { it() }
                } else {
                    onScrollToTop?.let { it() }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
}