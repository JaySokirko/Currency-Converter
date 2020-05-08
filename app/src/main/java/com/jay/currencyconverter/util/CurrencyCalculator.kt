package com.jay.currencyconverter.util


object CurrencyCalculator {

    fun calculateExchangeRate(currencyCoefficient: Double, inputValue: Double): Double {
        return currencyCoefficient.times(inputValue)
    }

    fun calculateExchangeCoefficient(baseCurrencyBid: Double, conversionCurrencyBid: Double) : Double {
        return baseCurrencyBid.div(conversionCurrencyBid)
    }
}