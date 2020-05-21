package com.jay.currencyconverter.service

import com.jay.currencyconverter.model.currencyExchange.bank.Banks
import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu
import io.reactivex.Observable
import retrofit2.http.GET


interface CurrencyExchangeRateApi {

    @GET("/ru/public/currency-cash.json#")
    fun getBanksExchangeRate() : Observable<Banks>

    @GET("/NBUStatService/v1/statdirectory/exchange?json")
    fun getNbuExchangeRate() : Observable<MutableList<Nbu>>
}