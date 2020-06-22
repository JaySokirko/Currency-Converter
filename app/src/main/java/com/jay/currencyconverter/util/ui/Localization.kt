package com.jay.currencyconverter.util.ui

import android.content.Context
import android.content.res.Configuration
import com.jay.currencyconverter.util.common.Constant.SELECTED_LANGUAGE
import com.jay.currencyconverter.util.common.StorageManager
import java.util.*


object Localization {

    val language: String
        get() = getDefaultLocale(Locale.getDefault().language)

    fun setLocale(context: Context, language: String)  {
        StorageManager.saveVariable(SELECTED_LANGUAGE, language)
        updateResourcesLegacy(context, language)
    }

    private fun getDefaultLocale(defaultLanguage: String): String {
        return StorageManager.getVariable(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun updateResourcesLegacy(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.locale = locale
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}