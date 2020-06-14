package com.jay.currencyconverter.model.exchangeRate

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable

interface Currency : Parcelable {

    val rate: Double
    fun getName(context: Context): String?
    fun getImage(context: Context): Drawable?
    fun getAbr(context: Context): String?
}