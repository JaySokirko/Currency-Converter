package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.OrganizationExchangeRateAdapter
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class BankActivityModule {

    @Provides
    fun provideBankExchangeRateAdapter(): OrganizationExchangeRateAdapter {
        return OrganizationExchangeRateAdapter()
    }

    companion object {
        @Provides
        fun provideBankActivityVM(activity: FragmentActivity): OrganizationActivityViewModel {
            return ViewModelProviders.of(activity).get(OrganizationActivityViewModel::class.java)
        }
    }
}