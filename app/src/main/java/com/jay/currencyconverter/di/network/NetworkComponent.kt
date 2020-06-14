package com.jay.currencyconverter.di.network

import com.jay.currencyconverter.annotation.OrganizationExchange
import com.jay.currencyconverter.annotation.NbuExchange
import com.jay.currencyconverter.annotation.NbuExchangeByDateAndCurrency
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import dagger.Component

@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    @get:OrganizationExchange
    val organizationExchange: CurrencyExchangeRateApi

    @get:NbuExchange
    val nbuExchange: CurrencyExchangeRateApi

    @get:NbuExchangeByDateAndCurrency
    val nbuExchangeByDateAndCurrency: CurrencyExchangeRateApi
}