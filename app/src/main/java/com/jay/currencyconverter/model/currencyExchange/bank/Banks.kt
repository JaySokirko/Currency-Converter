package com.jay.currencyconverter.model.currencyExchange.bank

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Banks {

    @SerializedName("sourceId")
    @Expose
    var sourceId: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("organizations")
    @Expose
    var organizations: List<Organization>? = null

}