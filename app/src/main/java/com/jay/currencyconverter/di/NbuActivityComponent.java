package com.jay.currencyconverter.di;

import com.jay.currencyconverter.ui.nbuActivity.INbuView;
import com.jay.currencyconverter.ui.nbuActivity.NbuActivity;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {NbuActivityModule.class}, dependencies = {BaseComponent.class})
public interface NbuActivityComponent {

    @Component.Builder
    interface Builder {
        Builder baseComponent(BaseComponent baseComponent);
        @BindsInstance
        Builder nbuPresenter(INbuView view);
        NbuActivityComponent build();
    }

    void inject(NbuActivity activity);
}
