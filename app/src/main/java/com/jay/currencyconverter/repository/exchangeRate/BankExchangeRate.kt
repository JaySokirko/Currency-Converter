package com.jay.currencyconverter.repository.exchangeRate

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganizations
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import io.reactivex.Observable

class BankExchangeRate {

    private val exchangeRate: CurrencyExchangeRateApi =
        BaseApplication.networkComponent.organizationExchange

    fun getExchangeRate(): Observable<CommonOrganizations> = exchangeRate.getBanksExchangeRate()
}