package com.jay.currencyconverter.repository

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu
import com.jay.currencyconverter.service.CurrencyExchangeRateApi
import io.reactivex.Observable

class NbuExchangeRate {

    private val exchangeRateApi: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.nbuExchange

    fun getExchangeRate(): Observable<MutableList<Nbu>> = exchangeRateApi.getNbuExchangeRate()
}