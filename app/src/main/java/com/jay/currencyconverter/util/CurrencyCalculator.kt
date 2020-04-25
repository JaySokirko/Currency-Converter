package com.jay.currencyconverter.util


object CurrencyCalculator {

    fun calculate(currencyCoefficient: Double, inputValue: Double): Double {
        return currencyCoefficient.times(inputValue)
    }


}