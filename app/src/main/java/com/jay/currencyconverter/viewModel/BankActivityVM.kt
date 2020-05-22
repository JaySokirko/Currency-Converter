package com.jay.currencyconverter.viewModel

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.model.currencyExchange.bank.Banks
import com.jay.currencyconverter.model.currencyExchange.bank.Organization
import com.jay.currencyconverter.repository.BankExchangeRate
import com.jay.currencyconverter.util.ResponseWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BankActivityVM : BaseViewModel() {

    val progressVisibility: ObservableInt = ObservableInt()
    val exchangeObserver: MutableLiveData<ResponseWrapper<List<Organization>>> = MutableLiveData()
    private val bankExchangeRate: BankExchangeRate = BankExchangeRate()

    fun getExchangeRate() {
        progressVisibility.set(View.VISIBLE)

        val subscribe: Disposable = bankExchangeRate.getExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result: Banks ->
                    exchangeObserver.postValue(ResponseWrapper(data = result.organizations))
                },

                { error: Throwable ->
                    exchangeObserver.postValue(ResponseWrapper(error = error))
                },

                { progressVisibility.set(View.GONE) }
            )

        disposable.add(subscribe)
    }
}