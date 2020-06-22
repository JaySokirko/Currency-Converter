package com.jay.currencyconverter.ui.organizationActivity

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganization
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganizations
import com.jay.currencyconverter.repository.exchangeRate.BankExchangeRate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class OrganizationActivityViewModel : ViewModel(), LifecycleObserver {

    val progressVisibility: ObservableInt = ObservableInt()
    val exchangeObserver: MutableLiveData<ResponseWrapper<List<CommonOrganization>>> = MutableLiveData()
    private val bankExchangeRate: BankExchangeRate = BankExchangeRate()
    private val disposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposable.clear()
    }

    fun getExchangeRate() {
        progressVisibility.set(View.VISIBLE)

        val subscribe: Disposable = bankExchangeRate.getExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result: CommonOrganizations ->
                    exchangeObserver.postValue(ResponseWrapper(data = result.organizations))
                },

                { error: Throwable -> exchangeObserver.postValue(ResponseWrapper(error = error)) },

                { progressVisibility.set(View.GONE) })

        disposable.add(subscribe)
    }
}