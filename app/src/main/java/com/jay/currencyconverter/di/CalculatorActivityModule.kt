package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.viewModel.CalculatorActivityVM
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
        fun provideCalculatorActivityVM(activity: FragmentActivity): CalculatorActivityVM {
            return ViewModelProviders.of(activity).get(CalculatorActivityVM::class.java)
        }
    }
}