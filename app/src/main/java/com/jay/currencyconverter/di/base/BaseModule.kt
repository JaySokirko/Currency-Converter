package com.jay.currencyconverter.di.base

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class BaseModule(private val application: Application) {

    @Provides
    fun providesApplication(): Application {
        return application
    }

}