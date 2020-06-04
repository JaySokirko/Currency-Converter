package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.ui.adapter.DisplayedCurrenciesAdapter
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.ui.nbuActivity.NbuActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class NbuActivityModule {

    @Provides
    fun provideNbuExchangeAdapter(): NbuExchangeAdapter {
        return NbuExchangeAdapter()
    }

    @Provides
    fun provideChartCurrenciesAdapter(): CurrencyChoiceAdapter {
        return CurrencyChoiceAdapter()
    }

    @Provides
    fun provideDisplayedCurrenciesAdapter(): DisplayedCurrenciesAdapter {
        return DisplayedCurrenciesAdapter()
    }

    companion object {
        @Provides
        fun provideNbuActivityVM(activity: FragmentActivity): NbuActivityViewModel {
            return ViewModelProviders.of(activity).get(NbuActivityViewModel::class.java)
        }
    }
}