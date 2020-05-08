package com.jay.currencyconverter.ui.bankActivity

import com.jay.currencyconverter.model.currencyExchange.bank.Organization
import com.jay.currencyconverter.ui.view.IView
import com.jay.currencyconverter.ui.view.IProgressView

interface IBankView: IProgressView, IView {

    fun onExchangeRateLoadFinish(list: List<Organization>)
    fun onExchangeRateLoadError(error: Throwable)
}