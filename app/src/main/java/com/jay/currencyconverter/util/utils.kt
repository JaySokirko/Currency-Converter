package com.jay.currencyconverter.util


fun String.removeLastChar(): String {
    return if (this.isEmpty()) ""
    else this.substring(0, this.length - 1)
}

/**
 *  This fun allows to work with multiple potential null params like
 *  @see kotlin.let
 */
inline fun <T1 : Any, T2 : Any, R : Any> letBlock(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}
