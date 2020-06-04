package com.jay.currencyconverter.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GsonConverter<T> {

    private val gson = Gson()
    private val type: Type = object : TypeToken<T>() {}.type

    fun convertDataToJson(data: T): String {
        return gson.toJson(data)
    }

    fun getDataFromJson(json: String): T {
        return gson.fromJson(json, type)
    }
}