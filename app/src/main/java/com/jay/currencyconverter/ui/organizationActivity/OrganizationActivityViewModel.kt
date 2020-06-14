package com.jay.currencyconverter.ui.organizationActivity

import android.util.Log
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganizations
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganization
import com.jay.currencyconverter.repository.exchangeRate.BankExchangeRate
import com.jay.currencyconverter.ui.BaseViewModel
import com.jay.currencyconverter.util.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class OrganizationActivityViewModel : BaseViewModel() {

    val progressVisibility: ObservableInt = ObservableInt()
    val exchangeObserver: MutableLiveData<ResponseWrapper<List<CommonOrganization>>> = MutableLiveData()
    private val bankExchangeRate: BankExchangeRate = BankExchangeRate()

    fun getExchangeRate() {
        progressVisibility.set(View.VISIBLE)

        val subscribe: Disposable = bankExchangeRate.getExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result: CommonOrganizations ->
                    exchangeObserver.postValue(ResponseWrapper(data = result.organizations))
                },

                { error: Throwable -> exchangeObserver.postValue(ResponseWrapper(error = error))
                    Log.d(TAG, "getExchangeRate: " + error.message)
                },

                { progressVisibility.set(View.GONE) }
            )

        disposable.add(subscribe)
    }
}