package com.jay.currencyconverter.model.currencyExchange.currency

import android.content.Context
import android.graphics.drawable.Drawable

abstract class Currency {
    abstract val bid: String?
    abstract val ask: String?
    abstract fun getName(context: Context): String?
    abstract fun getImage(context: Context): Drawable?
    abstract fun getAbr(context: Context): String?
}