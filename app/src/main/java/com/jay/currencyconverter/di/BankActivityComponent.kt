package com.jay.currencyconverter.di

import androidx.fragment.app.FragmentActivity
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [BankActivityModule::class])
interface BankActivityComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: FragmentActivity) : Builder
        fun build(): BankActivityComponent
    }

    fun inject(activity: OrganizationActivity)
}