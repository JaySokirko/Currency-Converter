package com.jay.currencyconverter.util

import androidx.databinding.ObservableField
import io.reactivex.subjects.PublishSubject

class ObservableFieldWrapper<T> : ObservableField<T>() {

    private var field: T? = null

    val publishSubject: PublishSubject<T> = PublishSubject.create()

    fun setField(field: T) {
        this.field = field
        publishSubject.onNext(field)
        set(field)
    }
}