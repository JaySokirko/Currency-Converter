package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.BankExchangeRateAdapter
import com.jay.currencyconverter.ui.bankActivity.BankActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class BankActivityModule {

    @Provides
    fun provideBankExchangeRateAdapter(): BankExchangeRateAdapter {
        return BankExchangeRateAdapter()
    }

    companion object {
        @Provides
        fun provideBankActivityVM(activity: FragmentActivity): BankActivityViewModel {
            return ViewModelProviders.of(activity).get(BankActivityViewModel::class.java)
        }
    }
}