package com.jay.currencyconverter.util

fun String.removeLastChar(string: String?): String? {
    return if (string == null || string.length == 0) null
    else string.substring(0, string.length - 1)
}