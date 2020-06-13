package com.jay.currencyconverter.model.exchangeRate.organization

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.model.exchangeRate.Currencies


class Organization() : Parcelable {

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

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        oldId = parcel.readInt()
        orgType = parcel.readInt()
        isBranch = parcel.readByte() != 0.toByte()
        title = parcel.readString()
        regionId = parcel.readString()
        cityId = parcel.readString()
        phone = parcel.readString()
        address = parcel.readString()
        link = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(oldId)
        parcel.writeInt(orgType)
        parcel.writeByte(if (isBranch) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(regionId)
        parcel.writeString(cityId)
        parcel.writeString(phone)
        parcel.writeString(address)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Organization> {
        override fun createFromParcel(parcel: Parcel): Organization {
            return Organization(
                parcel
            )
        }

        override fun newArray(size: Int): Array<Organization?> {
            return arrayOfNulls(size)
        }
    }
}