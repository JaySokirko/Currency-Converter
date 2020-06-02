package com.jay.currencyconverter.util.ui

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewTouchItemListener {

    //Gives scroll ability inside other scrollable views. For instance inside appbar.
    val listener: RecyclerView.OnItemTouchListener = object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            when (e.action) {
                MotionEvent.ACTION_MOVE -> rv.parent
                    .requestDisallowInterceptTouchEvent(true)
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }
}