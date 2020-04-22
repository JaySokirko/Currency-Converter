package com.jay.currencyconverter.service

import com.jay.currencyconverter.model.currencyExchange.Banks
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CurrencyExchangeRateApi {

    @GET("/ru/public/currency-cash.json#")
    fun getBanksExchangeRate() : Observable<Banks>

    companion object Factory{

        private const val BASE_URL = "https://resources.finance.ua"

        fun create(): CurrencyExchangeRateApi {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(CurrencyExchangeRateApi::class.java);
        }
    }
}