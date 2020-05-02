package com.jay.currencyconverter.util

import android.view.View

fun String.removeLastChar(string: String?): String? {
    return if (string.isNullOrEmpty()) null
    else string.substring(0, string.length - 1)
}