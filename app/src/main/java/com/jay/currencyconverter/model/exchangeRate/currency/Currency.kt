package com.jay.currencyconverter.model.exchangeRate.currency

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable

abstract class Currency : Parcelable, Cloneable {
    abstract val bid: String?
    abstract val ask: String?
    abstract fun getName(context: Context): String?
    abstract fun getImage(context: Context): Drawable?
    abstract fun getAbr(context: Context): String?

    var type: CurrencyType? = null

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super.clone()
    }
}