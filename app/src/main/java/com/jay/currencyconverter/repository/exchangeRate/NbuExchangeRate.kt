package com.jay.currencyconverter.repository.exchangeRate

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import io.reactivex.Observable

class NbuExchangeRate {

    private val exchangeRateApi: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.nbuExchange

    private val exchangeByDateAndCurrency: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.nbuExchangeByDateAndCurrency

    fun getExchangeRate(): Observable<MutableList<NbuCurrency>> = exchangeRateApi.getNbuExchangeRate()

    fun getExchangeByDateAndAbbr(abbreviation: String, date: String): Observable<MutableList<NbuCurrency>> {
        return exchangeByDateAndCurrency.getNbuExchangeByCurrencyAndDate(abbreviation, date)
    }

    fun createRequestList(currencyAbbreviation: String, dateList: List<String>):
            List<Observable<MutableList<NbuCurrency>>> {

        val requestList: MutableList<Observable<MutableList<NbuCurrency>>> = mutableListOf()

        dateList.forEach { date ->
            requestList.add(getExchangeByDateAndAbbr(currencyAbbreviation, date))
        }
        return requestList
    }
}