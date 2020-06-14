package com.jay.currencyconverter.di.organizationActivity

import androidx.fragment.app.FragmentActivity
import com.jay.currencyconverter.ui.organizationActivity.OrganizationActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [OrganizationActivityModule::class])
interface OrganizationActivityComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: FragmentActivity) : Builder
        fun build(): OrganizationActivityComponent
    }

    fun inject(activity: OrganizationActivity)
}