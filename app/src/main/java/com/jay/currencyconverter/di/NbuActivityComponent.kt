package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import com.jay.currencyconverter.databinding.ActivityNbuBinding
import com.jay.currencyconverter.ui.NbuActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NbuActivityModule::class])
interface NbuActivityComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: FragmentActivity): Builder
        fun build(): NbuActivityComponent
    }

    fun inject(activity: NbuActivity)
}