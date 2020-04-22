package com.jay.currencyconverter.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.Currencies
import com.jay.currencyconverter.model.currencyExchange.Organization

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val organization: Organization? = intent.getParcelableExtra("organization")
        val currencies:  Currencies? = intent.getParcelableExtra("currencies")
        Log.d("TAG", "onCreate: ${organization?.currencies?.allAvailableCurrencies}")
    }
}