package com.jay.currencyconverter.di

import android.app.Application
import dagger.Component

@Component(modules = [BaseModule::class])
interface BaseComponent {

    val application: Application
}