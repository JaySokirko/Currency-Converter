package com.jay.currencyconverter.model

import com.jay.currencyconverter.model.exchangeRate.currency.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.CurrencyType

class CurrencyChoice(
    val chosenCurrency: Currency? = null,
    val currencyType: CurrencyType? = null
)