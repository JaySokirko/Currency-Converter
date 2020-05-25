package com.jay.currencyconverter.di

import com.jay.currencyconverter.annotation.BankExchange
import com.jay.currencyconverter.annotation.NbuExchange
import com.jay.currencyconverter.annotation.NbuExchangeByDateAndCurrency
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    @get:BankExchange
    val bankExchange: CurrencyExchangeRateApi

    @get:NbuExchange
    val nbuExchange: CurrencyExchangeRateApi

    @get:NbuExchangeByDateAndCurrency
    val nbuExchangeByDateAndCurrency: CurrencyExchangeRateApi
}