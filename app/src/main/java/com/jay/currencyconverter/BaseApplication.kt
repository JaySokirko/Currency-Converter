package com.jay.currencyconverter

import android.app.Application
import androidx.room.Room
import com.jay.currencyconverter.di.*
import com.jay.currencyconverter.repository.room.DataBase

class BaseApplication : Application() {

    
    override fun onCreate() {
        baseComponent = DaggerBaseComponent.builder().baseModule(BaseModule(application = this)).build()
        networkComponent = DaggerNetworkComponent.create()
        dataBase = Room.databaseBuilder(this, DataBase::class.java, "DataBase").build()
        super.onCreate()
    }

    companion object {
        lateinit var baseComponent: BaseComponent
        lateinit var networkComponent: NetworkComponent
        lateinit var dataBase: DataBase
    }
}