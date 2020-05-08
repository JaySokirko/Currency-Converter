package com.jay.currencyconverter.ui.nbuActivity

import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu
import com.jay.currencyconverter.ui.view.IProgressView
import com.jay.currencyconverter.ui.view.IView

interface INbuView: IProgressView, IView {

    fun onExchangeRateLoadFinish(list: List<Nbu>)
    fun onExchangeRateLoadError(error: Throwable)
}