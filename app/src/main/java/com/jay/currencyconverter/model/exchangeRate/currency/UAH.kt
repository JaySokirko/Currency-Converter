package com.jay.currencyconverter.model.exchangeRate.currency

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.Currency

class UAH() : Currency() {

    override var ask: String? = "1"
    override var bid: String? = "1"

    constructor(parcel: Parcel) : this() {
        ask = parcel.readString()
        bid = parcel.readString()
    }

    override fun getImage(context: Context): Drawable? {
        return context.resources.getDrawable(R.drawable.ic_ukraine_flag)
    }

    override fun getName(context: Context): String? {
        return context.resources.getString(R.string.uah)
    }

    override fun getAbr(context: Context): String? {
        return context.resources.getString(R.string.UAH)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ask)
        parcel.writeString(bid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UAH> {
        override fun createFromParcel(parcel: Parcel): UAH {
            return UAH(parcel)
        }

        override fun newArray(size: Int): Array<UAH?> {
            return arrayOfNulls(size)
        }
    }
}