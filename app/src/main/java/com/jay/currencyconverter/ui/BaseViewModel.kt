package com.jay.currencyconverter.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val disposable = CompositeDisposable()

    fun onDestroy() {
        disposable.dispose()
        disposable.clear()
    }
}