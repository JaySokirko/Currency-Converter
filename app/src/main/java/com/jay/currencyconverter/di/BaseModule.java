package com.jay.currencyconverter.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {

    private Application application;

    public BaseModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application providesApplication() {
        return application;
    }
}
