package com.jay.currencyconverter.util.common

import java.lang.NullPointerException

class ValueChangeListener<T> {

    var value: T? = null
        set(value) {
            if (listener == null) throw NullPointerException("listener must be set")
            field = value
            listener?.onChange()
        }

    private var listener: OnValueChangeListener<T>? = null

    fun setListener(listener: OnValueChangeListener<T>) {
        this.listener = listener
    }

    interface OnValueChangeListener<T> {
        fun onChange()
    }
}