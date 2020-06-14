package com.jay.currencyconverter.model.exchangeRate

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NbuCurrency() : Currency, Parcelable, Cloneable {

    @Ignore
    private val currencies: Currencies = Currencies()

    @SerializedName("txt")
    @Expose
    @Ignore
    private var currencyName: String? = null

    @SerializedName("rate")
    @Expose
    @Ignore
    override var rate: Double = 0.0

    @SerializedName("cc")
    @Expose
    @ColumnInfo(name = "abbr")
    var currencyAbbreviation: String? = null

    @SerializedName("exchangedate")
    @Expose
    @Ignore
    var exchangeDate: String? = null

    @Ignore
    var currenciesList: ArrayList<Currency> = ArrayList()

    constructor(parcel: Parcel) : this() {
        currencyName = parcel.readString()
        rate = parcel.readDouble()
        currencyAbbreviation = parcel.readString()
        exchangeDate = parcel.readString()
    }

    override fun getName(context: Context): String? {
        return currencies.getCurrencyNameByAbr(currencyAbbreviation, context)
    }

    override fun getImage(context: Context): Drawable? {
        currencyAbbreviation ?: return null
        return currencies.getCurrencyImageByAbr(currencyAbbreviation!!, context)
    }

    override fun getAbr(context: Context): String? = currencyAbbreviation

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currencyName)
        parcel.writeDouble(rate)
        parcel.writeString(currencyAbbreviation)
        parcel.writeString(exchangeDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NbuCurrency> {
        override fun createFromParcel(parcel: Parcel): NbuCurrency {
            return NbuCurrency(parcel)
        }

        override fun newArray(size: Int): Array<NbuCurrency?> = arrayOfNulls(size)
    }
}