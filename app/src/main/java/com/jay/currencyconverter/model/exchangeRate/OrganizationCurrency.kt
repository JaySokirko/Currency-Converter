package com.jay.currencyconverter.model.exchangeRate

import android.os.Parcelable

abstract class OrganizationCurrency : Currency, Parcelable, Cloneable {

    abstract val bid: String?
    abstract val ask: String?

    var type: CurrencyType? = null

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super.clone()
    }
}