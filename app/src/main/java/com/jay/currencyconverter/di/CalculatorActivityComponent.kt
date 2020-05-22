package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import com.jay.currencyconverter.ui.CalculatorActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [CalculatorActivityModule::class])
interface CalculatorActivityComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun activity(activity: FragmentActivity): Builder
        fun build(): CalculatorActivityComponent
    }

    fun inject(activity: CalculatorActivity)
}