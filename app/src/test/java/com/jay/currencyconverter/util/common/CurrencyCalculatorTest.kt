package com.jay.currencyconverter.util.common

import com.jay.currencyconverter.util.round
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class CurrencyCalculatorTest {

    private val value1 : BigDecimal = BigDecimal.valueOf(2.1234)
    private val value2 : BigDecimal = BigDecimal.valueOf(4.1234)

    @Test
    fun calculateResult() {
        val actual: BigDecimal = CurrencyCalculator.calculateResult(value1, value2)
        val expected: BigDecimal = BigDecimal.valueOf(8.75562756).round(4)
        assertEquals(expected, actual)
    }

    @Test
    fun calculateExchangeRate() {
        val actual: BigDecimal = CurrencyCalculator.calculateExchangeRate(value1, value2)
        val expected: BigDecimal = BigDecimal.valueOf(0.5150).round(4)
        assertEquals(expected, actual)
    }
}