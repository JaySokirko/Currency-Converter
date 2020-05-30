package com.jay.currencyconverter.repository

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import io.reactivex.Observable

class NbuExchangeRate {

    private val exchangeRateApi: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.nbuExchange

    private val exchangeByDateAndCurrency: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.nbuExchangeByDateAndCurrency

    fun getExchangeRate(): Observable<MutableList<Nbu>> = exchangeRateApi.getNbuExchangeRate()

    fun getExchangeByDateAndCurrency(currencyAbr: String, date: String):
            Observable<MutableList<Nbu>> {
        return exchangeByDateAndCurrency.getNbuExchangeByCurrencyAndDate(currencyAbr, date)
    }

    fun createRequestList(currencyAbr: String, dateList: List<String>):
            List<Observable<MutableList<Nbu>>> {

        val requestList: MutableList<Observable<MutableList<Nbu>>> = mutableListOf()

        dateList.forEach { date ->
            requestList.add(getExchangeByDateAndCurrency(currencyAbr, date))
        }
        return requestList
    }
}