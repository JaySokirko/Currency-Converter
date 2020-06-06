package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.ui.adapter.DisplayedCurrenciesAdapter
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.ui.dialog.NoCurrencyChosenDialog
import com.jay.currencyconverter.ui.nbuActivity.NbuActivityViewModel
import com.jay.currencyconverter.ui.nbuActivity.NbuCurrenciesDisplay
import com.jay.currencyconverter.util.ui.LayoutParamsAnimator
import dagger.Module
import dagger.Provides

@Module
class NbuActivityModule {

    @Provides
    fun provideNbuExchangeAdapter(): NbuExchangeAdapter = NbuExchangeAdapter()

    @Provides
    fun provideChartCurrenciesAdapter(): CurrencyChoiceAdapter = CurrencyChoiceAdapter()

    @Provides
    fun provideDisplayedCurrenciesAdapter(): DisplayedCurrenciesAdapter = DisplayedCurrenciesAdapter()

    @Provides
    fun provideNbuCurrenciesDisplay(): NbuCurrenciesDisplay = NbuCurrenciesDisplay()

    @Provides
    fun provideErrorDialog(): ErrorDialog = ErrorDialog()

    @Provides
    fun provideNoCurrencyChosenDialog() : NoCurrencyChosenDialog = NoCurrencyChosenDialog()

    @Provides
    fun provideLayoutParamsAnimator(): LayoutParamsAnimator = LayoutParamsAnimator()

    companion object {
        @Provides
        fun provideNbuActivityVM(activity: FragmentActivity): NbuActivityViewModel {
            return ViewModelProviders.of(activity).get(NbuActivityViewModel::class.java)
        }
    }
}