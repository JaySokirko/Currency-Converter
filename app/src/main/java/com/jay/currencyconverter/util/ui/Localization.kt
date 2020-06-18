package com.jay.currencyconverter.util.ui

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.jay.currencyconverter.util.common.Constant.SELECTED_LANGUAGE
import com.jay.currencyconverter.util.common.StorageManager

import java.util.*

object Localization {

    val language: String
        get() = getDefaultLocale(Locale.getDefault().language)

    fun setLocale(context: Context, language: String)  {
        StorageManager.saveVariable(SELECTED_LANGUAGE, language)

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else {
            updateResourcesLegacy(context, language)
        }
    }

    private fun getDefaultLocale(defaultLanguage: String): String {
        return StorageManager.getVariable(SELECTED_LANGUAGE, defaultLanguage)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}