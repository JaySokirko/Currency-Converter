package com.jay.currencyconverter;

import android.app.Application;

import com.jay.currencyconverter.di.BaseComponent;
import com.jay.currencyconverter.di.BaseModule;
import com.jay.currencyconverter.di.DaggerBaseComponent;
import com.jay.currencyconverter.di.DaggerNetworkComponent;
import com.jay.currencyconverter.di.NetworkComponent;

public class BaseApplication extends Application {

    private static BaseComponent baseComponent;
    private static NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        baseComponent = DaggerBaseComponent.builder()
                .baseModule(new BaseModule(this))
                .build();

        networkComponent = DaggerNetworkComponent.create();
        super.onCreate();
    }

    public static BaseComponent getBaseComponent() {
        return baseComponent;
    }

    public static NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
