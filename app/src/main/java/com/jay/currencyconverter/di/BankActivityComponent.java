package com.jay.currencyconverter.di;

import com.jay.currencyconverter.ui.bankActivity.BankActivity;
import com.jay.currencyconverter.ui.bankActivity.IBankView;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {BankActivityModule.class})
public interface BankActivityComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bankPresenter(IBankView view);
        BankActivityComponent build();
    }

    void inject(BankActivity activity);
}
