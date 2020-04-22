package com.jay.currencyconverter.model.currencyExchange.currency

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.R

class PKR() : Currency() {
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
        return context.resources.getString(R.string.pakistani_rupees)
    }

    override fun getImage(context: Context): Drawable? {
        return context.resources.getDrawable(R.drawable.ic_pakistan_flag)
    }

    override fun getAbr(context: Context): String? {
        return context.resources.getString(R.string.PKR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ask)
        parcel.writeString(bid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PKR> {
        override fun createFromParcel(parcel: Parcel): PKR {
            return PKR(parcel)
        }

        override fun newArray(size: Int): Array<PKR?> {
            return arrayOfNulls(size)
        }
    }
}