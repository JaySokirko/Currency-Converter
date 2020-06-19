package com.jay.currencyconverter.ui.adapter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.ui.adapter.viewHolder.AnimatedViewHolder
import com.jay.currencyconverter.util.ui.SmoothScroller

abstract class AnimatedRecyclerAdapter<T> : RecyclerView.Adapter<AnimatedViewHolder<T>>() {

    protected var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onViewDetachedFromWindow(holder: AnimatedViewHolder<T>) {
        holder.clearAnimation()
        super.onViewDetachedFromWindow(holder)
    }

    protected fun smoothScrollToTop(){
        SmoothScroller.getSmoothScroller().targetPosition = 0
        recyclerView?.layoutManager?.startSmoothScroll(SmoothScroller.getSmoothScroller())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        AnimatedViewHolder.clearViewHolderIds()
    }
}