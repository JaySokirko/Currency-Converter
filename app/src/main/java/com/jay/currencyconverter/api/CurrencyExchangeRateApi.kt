package com.jay.currencyconverter.api

import com.jay.currencyconverter.model.exchangeRate.bank.Banks
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyExchangeRateApi {

    @GET("/ru/public/currency-cash.json#")
    fun getBanksExchangeRate(): Observable<Banks>

    @GET("/NBUStatService/v1/statdirectory/exchange?json")
    fun getNbuExchangeRate(): Observable<MutableList<Nbu>>

    @GET("/NBUStatService/v1/statdirectory/exchange?json")
    fun getNbuExchangeByCurrencyAndDate(@Query("valcode") code: String,
                                        @Query("date") date: String ): Observable<MutableList<Nbu>>

}