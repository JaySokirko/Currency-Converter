package com.jay.currencyconverter.util.common

import java.lang.Exception
import java.lang.StringBuilder

object NumberFormatter {

    /**
     * 1234.1111 -> 1 234.1111
     * 12345.1111 -> 12 345.1111
     * 123456.1111 -> 123 456.1111
     * 1234567.1111 -> 1 234 567.1111
     * 12345678.1111 -> 12 345 678.1111
     * 123456789.1111 -> 123 456 789.111
     * 1234567890.1111 -> 1 234 567 890.111
     * 12345678910.1111 -> 12 345 678 910.111
     * 123456789120.1111 -> 123 456 789 120.111
     */
    fun format(input: String): String {
        val inputValue  = input.replace("\\s".toRegex(), "")

        if (inputValue.length < 4) return inputValue

        try {
            val array = inputValue.split(".")
            val integer = array[0]
            val fractional = if (array.size > 1) "." + array[1] else ""
            val reformattedInteger = StringBuilder()

            reformattedInteger.append(integer)

            when (integer.length) {
                4 -> { reformattedInteger.insert(1, " ") }
                5 -> { reformattedInteger.insert(2, " ") }
                6 -> { reformattedInteger.insert(3, " ") }
                7 -> { reformattedInteger.insert(1, " ").insert(5, " ") }
                8 -> { reformattedInteger.insert(2, " ").insert(6, " ") }
                9 -> { reformattedInteger.insert(3, " ").insert(7, " ") }
                10 -> { reformattedInteger.insert(1, " ").insert(5, " ").insert(9, " ") }
                11 -> { reformattedInteger.insert(2, " ").insert(6, " ").insert(10, " ") }
                12 -> { reformattedInteger.insert(3, " ").insert(7, " ").insert(11, " ") }
            }

            return reformattedInteger.append(fractional).toString()

        } catch (e: Exception){
            e.printStackTrace()
            return input
        }
    }
}