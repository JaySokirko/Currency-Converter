package com.jay.currencyconverter.model.currencyExchange

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Organization {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("oldId")
    @Expose
    var oldId = 0

    @SerializedName("orgType")
    @Expose
    var orgType = 0

    @SerializedName("branch")
    @Expose
    var isBranch = false

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("regionId")
    @Expose
    var regionId: String? = null

    @SerializedName("cityId")
    @Expose
    var cityId: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("link")
    @Expose
    var link: String? = null

    @SerializedName("currencies")
    @Expose
    var currencies: Currencies? = null

}