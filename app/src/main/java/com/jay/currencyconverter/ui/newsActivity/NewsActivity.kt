package com.jay.currencyconverter.ui.newsActivity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.jay.currencyconverter.R
import com.jay.currencyconverter.ui.NavigationActivity
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : NavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_news, R.layout.default_toolbar)
    }
}