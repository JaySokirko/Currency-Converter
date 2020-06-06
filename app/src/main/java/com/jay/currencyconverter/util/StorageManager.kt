package com.jay.currencyconverter.util

import android.content.Context
import android.content.SharedPreferences
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.util.Constant.COMMON

object StorageManager {

    private val context: Context = BaseApplication.baseComponent.application.baseContext
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(COMMON, Context.MODE_PRIVATE)

    fun saveVariable(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun saveVariable(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun saveVariable(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getVariable(key: String, default: String): String {
        return sharedPreferences.getString(key, default).toString()
    }

    fun getVariable(key: String, default: Int): Int = sharedPreferences.getInt(key, default)

    fun getVariable(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }
}