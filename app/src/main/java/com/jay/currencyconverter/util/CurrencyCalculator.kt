package com.jay.currencyconverter.util

import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.model.currencyExchange.currency.UAH
import com.jay.currencyconverter.model.currencyExchange.currency.USD


object CurrencyCalculator {

    fun calculate(fromCurrency: Currency, toCurrency: Currency, inputValue: Double,
    exchangeRate: Double): Double {
        if (fromCurrency is UAH && toCurrency is USD) {
            return inputValue / exchangeRate
        } else if (fromCurrency is USD && toCurrency is UAH) {
             return inputValue * exchangeRate
        }
        return 0.0
    }


}