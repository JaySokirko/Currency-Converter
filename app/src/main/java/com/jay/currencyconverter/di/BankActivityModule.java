package com.jay.currencyconverter.di;

import com.jay.currencyconverter.ui.adapter.BankExchangeRateAdapter;
import com.jay.currencyconverter.ui.bankActivity.BankPresenter;
import com.jay.currencyconverter.ui.bankActivity.IBankView;

import dagger.Module;
import dagger.Provides;

@Module
public class BankActivityModule {

    @Provides
    public BankExchangeRateAdapter provideBankExchangeRateAdapter() {
        return new BankExchangeRateAdapter();
    }

    @Provides
    public static BankPresenter provideBankPresenter(IBankView view) {
        return new BankPresenter(view);
    }
}
