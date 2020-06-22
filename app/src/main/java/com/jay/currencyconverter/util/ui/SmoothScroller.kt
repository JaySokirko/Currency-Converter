package com.jay.currencyconverter.util.ui

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.BaseApplication

object SmoothScroller {

    private val context: Context = BaseApplication.baseComponent.application.applicationContext

    private val smoothScroller: RecyclerView.SmoothScroller = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
    }

    fun getSmoothScroller(): RecyclerView.SmoothScroller = smoothScroller

    fun scrollToPosition(layoutManager: LinearLayoutManager, position: Int) {
        SmoothScroller.getSmoothScroller().targetPosition = position
        layoutManager.startSmoothScroll(SmoothScroller.getSmoothScroller())
    }
}