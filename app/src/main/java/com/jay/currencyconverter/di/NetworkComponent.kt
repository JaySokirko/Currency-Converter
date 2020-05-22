package com.jay.currencyconverter.di

import com.jay.currencyconverter.annotation.BankExchange
import com.jay.currencyconverter.annotation.NbuExchange
import com.jay.currencyconverter.service.CurrencyExchangeRateApi
import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    @get:BankExchange
    val bankExchange: CurrencyExchangeRateApi

    @get:NbuExchange
    val nbuExchange: CurrencyExchangeRateApi
}