package com.jay.currencyconverter.model

import com.jay.currencyconverter.model.exchangeRate.currency.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.CurrencyType

class CurrencyChoiceWrapper(
    val chosenCurrency: Currency? = null,
    val currencyType: CurrencyType? = null
)