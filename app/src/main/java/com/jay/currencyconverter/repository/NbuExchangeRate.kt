package com.jay.currencyconverter.repository

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.model.exchangeRate.nbu.NbuCurrencyByDate
import io.reactivex.Observable
import retrofit2.Call

class NbuExchangeRate {

    private val exchangeRateApi: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.nbuExchange

    private val exchangebyDateAndCurrency: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.nbuExchangeByDateAndCurrency

    fun getExchangeRate(): Observable<MutableList<Nbu>> = exchangeRateApi.getNbuExchangeRate()

    fun getExchangeByDateAndCurrency(currencyAbr: String, date: String): Observable<MutableList<NbuCurrencyByDate>> =
        exchangebyDateAndCurrency.getNbuExchangeByCurrencyAndDate(currencyAbr, date)
}