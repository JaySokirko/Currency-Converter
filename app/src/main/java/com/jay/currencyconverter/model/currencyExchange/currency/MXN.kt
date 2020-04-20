package com.jay.currencyconverter.model.currencyExchange.currency

import android.content.Context
import android.graphics.drawable.Drawable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.R

class MXN : Currency() {
    @SerializedName("ask")
    @Expose
    override var ask: String? = null

    @SerializedName("bid")
    @Expose
    override var bid: String? = null

    override fun getName(context: Context): String? {
        return context.resources.getString(R.string.mexican_new_pesos)
    }

    override fun getImage(context: Context): Drawable? {
        return context.resources.getDrawable(R.drawable.ic_mexico_flag)
    }

    override fun getAbr(context: Context): String? {
        return context.resources.getString(R.string.MXN)
    }
}