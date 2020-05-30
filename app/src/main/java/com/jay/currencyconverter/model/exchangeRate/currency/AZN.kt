package com.jay.currencyconverter.model.exchangeRate.currency

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.R

class AZN() : Currency() {

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
        return context.resources.getString(R.string.azerbaijani_manats)
    }

    override fun getImage(context: Context): Drawable? {
       return context.resources.getDrawable(R.drawable.ic_azerbaijan_flag)
    }

    override fun getAbr(context: Context): String? {
        return context.resources.getString(R.string.AZN)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ask)
        parcel.writeString(bid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AZN> {
        override fun createFromParcel(parcel: Parcel): AZN {
            return AZN(parcel)
        }

        override fun newArray(size: Int): Array<AZN?> {
            return arrayOfNulls(size)
        }
    }
}