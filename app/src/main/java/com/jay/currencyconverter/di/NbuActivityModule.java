package com.jay.currencyconverter.di;

import android.app.Application;

import com.jay.currencyconverter.annotation.ApplicationContext;
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter;
import com.jay.currencyconverter.ui.nbuActivity.INbuView;
import com.jay.currencyconverter.ui.nbuActivity.NbuPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NbuActivityModule {

    @Provides
    public NbuExchangeAdapter provideNbuExchangeAdapter() {
        return new NbuExchangeAdapter();
    }

    @Provides
    public static NbuPresenter provideNbuPresenter(INbuView view, Application app) {
        return new NbuPresenter(view, app.getBaseContext());
    }
}
