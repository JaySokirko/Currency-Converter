package com.jay.currencyconverter.di.calculatorActivity

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter.HorizontalCurrencyButtonsAdapter
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class CalculatorActivityModule {

    @Provides
    fun provideCurrencyChoiceAdapter(): HorizontalCurrencyButtonsAdapter {
        return HorizontalCurrencyButtonsAdapter()
    }

    companion object {
        @Provides
        fun provideCalculatorActivityVM(activity: FragmentActivity): CalculatorActivityViewModel {
            return ViewModelProviders.of(activity).get(CalculatorActivityViewModel::class.java)
        }
    }
}