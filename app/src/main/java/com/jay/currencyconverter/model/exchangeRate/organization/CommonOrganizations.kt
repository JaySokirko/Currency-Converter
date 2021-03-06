package com.jay.currencyconverter.model.exchangeRate.organization

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommonOrganizations {

    @SerializedName("sourceId")
    @Expose
    var sourceId: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("organizations")
    @Expose
    var organizations: List<CommonOrganization>? = null

}