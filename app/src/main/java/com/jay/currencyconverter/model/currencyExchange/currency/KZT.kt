package com.jay.currencyconverter.model.currencyExchange.currency

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.R

class KZT() : Currency() {
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

    override fun getName(context: Context): String? {
        return context.resources.getString(R.string.kazakh_tenge)
    }

    override fun getImage(context: Context): Drawable? {
        return context.resources.getDrawable(R.drawable.ic_kazakhstan_flag)
    }

    override fun getAbr(context: Context): String? {
        return context.resources.getString(R.string.KZT)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ask)
        parcel.writeString(bid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KZT> {
        override fun createFromParcel(parcel: Parcel): KZT {
            return KZT(parcel)
        }

        override fun newArray(size: Int): Array<KZT?> {
            return arrayOfNulls(size)
        }
    }
}