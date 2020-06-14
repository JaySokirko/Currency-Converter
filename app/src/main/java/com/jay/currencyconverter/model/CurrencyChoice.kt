package com.jay.currencyconverter.model

import com.jay.currencyconverter.model.exchangeRate.Currency
import com.jay.currencyconverter.model.exchangeRate.CurrencyType

class CurrencyChoice(
    val chosenCurrency: Currency? = null,
    val currencyType: CurrencyType? = null,
    val isSelected: Boolean? = null
)