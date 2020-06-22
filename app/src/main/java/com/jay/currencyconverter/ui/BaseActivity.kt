package com.jay.currencyconverter.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.jay.currencyconverter.util.ui.Localization
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity: AppCompatActivity() {

    protected val disposable = CompositeDisposable()
    protected var inflater: LayoutInflater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Localization.setLocale(this, Localization.language)

        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}