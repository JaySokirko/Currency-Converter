package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class CalculatorActivityModule {

    @Provides
    fun provideCurrencyChoiceAdapter(): CurrencyChoiceAdapter {
        return CurrencyChoiceAdapter()
    }

    companion object {
        @Provides
        fun provideCalculatorActivityVM(activity: FragmentActivity): CalculatorActivityViewModel {
            return ViewModelProviders.of(activity).get(CalculatorActivityViewModel::class.java)
        }
    }
}