package com.jay.currencyconverter.util

import java.math.BigDecimal
import java.math.RoundingMode

inline fun <T1 : Any, T2 : Any, R : Any> executeIfParamsNotNull(p1: T1?, p2: T2?,
                                                                execute: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) execute(p1, p2) else null
}

fun <T> MutableList<T>.removeLast()  {
    if (isEmpty()) return
    else removeAt(lastIndex)
}

fun String.removeLastChar(): String {
    return if (this.isEmpty()) ""
    else this.substring(0, this.length - 1)
}

fun Double.roundToFloat(places: Int = 2): Float {
    var decimal: BigDecimal = BigDecimal.valueOf(this)
    decimal = decimal.setScale(places, RoundingMode.HALF_UP)
    return decimal.toFloat()
}

fun BigDecimal.round(places: Int = 4): BigDecimal {
    var decimal: BigDecimal = this
    decimal = decimal.setScale(places, RoundingMode.HALF_UP)
    return decimal
}