package com.jay.currencyconverter.ui.interBankActivity

import com.jay.currencyconverter.ui.view.IProgressView
import com.jay.currencyconverter.ui.view.IView

interface IInterBankView: IProgressView, IView {

    fun OnInterBankExchangeRateLoadFinish()
    fun onInterBankExhangeRateLoadFailure(throwable: Throwable)
}