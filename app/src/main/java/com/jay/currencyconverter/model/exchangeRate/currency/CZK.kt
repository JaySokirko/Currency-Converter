package com.jay.currencyconverter.model.exchangeRate.currency

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.OrganizationCurrency

class CZK() : OrganizationCurrency() {

    @SerializedName("ask")
    @Expose
    override var ask: String? = null

    @SerializedName("bid")
    @Expose
    override var bid: String? = null

    constructor(parcel: Parcel) : this() {
        ask = parcel.readString()
        bid = parcel.readString()
    }

    override val rate: Double
        get() {
            bid?.let { return it.toDouble() }
            return 0.0
        }

    override fun getName(context: Context): String? {
        return context.resources.getString(R.string.czech_crowns)
    }

    override fun getImage(context: Context): Drawable? {
        return context.resources.getDrawable(R.drawable.ic_czech_flag)
    }

    override fun getAbr(context: Context): String? {
        return context.resources.getString(R.string.CZK)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ask)
        parcel.writeString(bid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CZK> {
        override fun createFromParcel(parcel: Parcel): CZK {
            return CZK(parcel)
        }

        override fun newArray(size: Int): Array<CZK?> {
            return arrayOfNulls(size)
        }
    }
}