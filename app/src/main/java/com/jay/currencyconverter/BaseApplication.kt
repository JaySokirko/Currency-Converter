package com.jay.currencyconverter

import android.app.Application
import com.jay.currencyconverter.di.*

class BaseApplication : Application() {

    override fun onCreate() {
        baseComponent = DaggerBaseComponent.builder().baseModule(BaseModule(this)).build()
        networkComponent = DaggerNetworkComponent.create()
        super.onCreate()
    }

    companion object {
        lateinit var baseComponent: BaseComponent
        lateinit var networkComponent: NetworkComponent

    }
}