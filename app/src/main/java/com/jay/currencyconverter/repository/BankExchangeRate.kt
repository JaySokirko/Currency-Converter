package com.jay.currencyconverter.repository

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.currencyExchange.bank.Banks
import com.jay.currencyconverter.service.CurrencyExchangeRateApi
import io.reactivex.Observable

class BankExchangeRate {

    private val exchangeRate: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.bankExchange

    fun getExchangeRate(): Observable<Banks> = exchangeRate.getBanksExchangeRate()
}