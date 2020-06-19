package com.jay.currencyconverter.model.exchangeRate.organization

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R

class NbuOrganization() : Organization {

    private val context: Context = BaseApplication.baseComponent.application.applicationContext

    override var title: String? = context.resources.getString(R.string.nbu_title)
    override var phone: String? = "044 230 1844"

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        phone = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NbuOrganization> {
        override fun createFromParcel(parcel: Parcel): NbuOrganization {
            return NbuOrganization(parcel)
        }

        override fun newArray(size: Int): Array<NbuOrganization?> {
            return arrayOfNulls(size)
        }
    }

}