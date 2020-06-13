package com.jay.currencyconverter.repository.exchangeRate

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.exchangeRate.organization.Organizations
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import io.reactivex.Observable

class BankExchangeRate {

    private val exchangeRate: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.bankExchange

    fun getExchangeRate(): Observable<Organizations> = exchangeRate.getBanksExchangeRate()
}