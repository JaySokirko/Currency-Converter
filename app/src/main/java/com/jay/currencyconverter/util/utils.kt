package com.jay.currencyconverter.util

fun String.removeLastChar(string: String?): String? {
    return if (string.isNullOrEmpty()) null
    else string.substring(0, string.length - 1)
}