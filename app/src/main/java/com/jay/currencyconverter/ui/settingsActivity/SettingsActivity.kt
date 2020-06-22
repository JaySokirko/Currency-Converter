package com.jay.currencyconverter.ui.settingsActivity

import android.os.Bundle
import com.jay.currencyconverter.R
import com.jay.currencyconverter.ui.NavigationActivity

class SettingsActivity : NavigationActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_settings)

    }
}