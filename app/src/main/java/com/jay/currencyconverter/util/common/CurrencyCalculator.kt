package com.jay.currencyconverter.util.common

import com.jay.currencyconverter.util.round
import java.math.BigDecimal
import java.math.MathContext


object CurrencyCalculator {

    fun calculateResult(exchangeRate: BigDecimal, enteredValue: BigDecimal): BigDecimal {
        return exchangeRate.times(enteredValue).round(places = 4)
    }

    fun calculateExchangeRate(baseCurrencyRate: BigDecimal, conversionCurrencyRate: BigDecimal) : BigDecimal {
        return baseCurrencyRate.divide(conversionCurrencyRate, MathContext.DECIMAL32).round(places = 4)
    }
}