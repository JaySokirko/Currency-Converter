package com.jay.currencyconverter.model.exchangeRate.nbu

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NbuCurrencyByDate {

    @SerializedName("r030")
    @Expose
    var r030 = 0

    @SerializedName("txt")
    @Expose
    var currencyName: String? = null

    @SerializedName("rate")
    @Expose
    var rate = 0.0

    @SerializedName("cc")
    @Expose
    var currencyAbbreviation: String? = null

    @SerializedName("exchangedate")
    @Expose
    var exchangeDate: String? = null
}