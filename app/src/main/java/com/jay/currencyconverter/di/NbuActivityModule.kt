package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.ChartCurrencyAdapter
import com.jay.currencyconverter.ui.adapter.DisplayedCurrenciesAdapter
import com.jay.currencyconverter.ui.adapter.NbuExchangeRateAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.ui.dialog.NoCurrencyChosenDialog
import com.jay.currencyconverter.ui.nbuActivity.MainContentViewModel
import com.jay.currencyconverter.ui.nbuActivity.AppbarViewModel
import com.jay.currencyconverter.animation.LayoutParamsAnimation
import dagger.Module
import dagger.Provides

@Module
class NbuActivityModule {

    @Provides
    fun provideNbuExchangeAdapter(): NbuExchangeRateAdapter = NbuExchangeRateAdapter()

    @Provides
    fun provideChartCurrenciesAdapter(): ChartCurrencyAdapter = ChartCurrencyAdapter()

    @Provides
    fun provideDisplayedCurrenciesAdapter(): DisplayedCurrenciesAdapter = DisplayedCurrenciesAdapter()

//    @Provides
//    fun provideNbuCurrenciesDisplay(): NbuDatabaseManager = NbuDatabaseManager()

    @Provides
    fun provideErrorDialog(): ErrorDialog = ErrorDialog()

    @Provides
    fun provideNoCurrencyChosenDialog() : NoCurrencyChosenDialog = NoCurrencyChosenDialog()

    @Provides
    fun provideLayoutParamsAnimator(): LayoutParamsAnimation =
        LayoutParamsAnimation()

    companion object {
        @Provides
        fun provideNbuExchangeRateViewModel(activity: FragmentActivity): MainContentViewModel {
            return ViewModelProviders.of(activity).get(MainContentViewModel::class.java)
        }

        @Provides
        fun providePreviousNbuExchangeViewModel(activity: FragmentActivity): AppbarViewModel {
            return ViewModelProviders.of(activity).get(AppbarViewModel::class.java)
        }
    }
}