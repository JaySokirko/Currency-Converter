package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.viewModel.NbuActivityMV
import dagger.Module
import dagger.Provides

@Module
class NbuActivityModule {

    @Provides
    fun provideNbuExchangeAdapter(): NbuExchangeAdapter {
        return NbuExchangeAdapter()
    }

    companion object {
        @Provides
        fun provideNbuActivityVM(activity: FragmentActivity): NbuActivityMV {
            return ViewModelProviders.of(activity).get(NbuActivityMV::class.java)
        }
    }
}