package com.jay.currencyconverter.util.common

import android.os.Build

object Constant {
    const val BANKS_EXCHANGE_URL = "https://resources.finance.ua"
    const val NBU_URL = "https://bank.gov.ua"

    const val ORGANIZATION = "organization"
    const val CURRENCIES = "currencies"
    const val NBU_CURRENCIES = "nbuCurrencies"
    const val EMPTY_STRING = ""
    const val COMMON = "Common"
    const val CURRENCY_ABR = "currencyAbr"
    const val CURRENCY_ABR_DEFAULT = "USD"
    const val LAST_CHECKED_POSITION = "lastCheckedPosition"
    const val MAIN_CHECKBOX_CHECKED = "mainCheckBoxChecked"
    const val OPENED_ACTIVITY = "previousOpenedActivity"
    const val SELECTED_LANGUAGE = "selectedLanguage"
    const val ENGLISH_LANGUAGE = "en"
    const val UKRAINIAN_LANGUAGE = "default"
    const val CALCULATOR_HINTS_NOT_SHOWN = "calculatorHintsNotShown"
    const val OPEN_CALCULATOR_HINT_NOT_SHOWN = "openCalculatorHint"
    const val DISPLAYED_CURRENCIES_SETTINGS_HINT_NOT_SHOWN = "displayedCurrenciesSettingsNotShown"
    const val SSL_HAND_SHAKE_ABORTED_MSG = "SSL handshake aborted"

    const val CURRENCIES_NOT_CHOSEN = 0
    const val CURRENCIES_CHOSEN = 1
    const val CANCEL_ALL_CURRENCY_CHOICE = 0
    const val CANCEL_BASE_CURRENCY_CHOICE = 1
    const val CANCEL_CONVERSION_CURRENCY_CHOICE = 2
    const val NBU_ACTIVITY = 0
    const val ORGANIZATION_ACTIVITY = 1
    const val SCROLL_BOTTOM = 1

    val ABOVE_API_22 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    val ABOVE_API_23 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
}