package com.jay.currencyconverter.model.exchangeRate.nbu

import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Nbu {

    @SerializedName("txt")
    @Expose
    @Ignore
    var currencyName: String? = null

    @SerializedName("rate")
    @Expose
    @Ignore
    var rate = 0.0

    @SerializedName("cc")
    @Expose
    @ColumnInfo(name = "abbr")
    var currencyAbbreviation: String? = null

    @SerializedName("exchangedate")
    @Expose
    @Ignore
    var exchangeDate: String? = null
}