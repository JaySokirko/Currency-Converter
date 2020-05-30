package com.jay.currencyconverter.viewModel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.exchangeRate.currency.Currencies
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.repository.NbuExchangeRate
import com.jay.currencyconverter.util.ResponseWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NbuActivityMV : BaseViewModel() {

    val progressVisibility: ObservableInt = ObservableInt()
    val exchangeObserver: MutableLiveData<ResponseWrapper<List<Nbu>>> = MutableLiveData()
    private val currencies: Currencies = Currencies()
    private val nbuExchangeRate: NbuExchangeRate = NbuExchangeRate()
    private val context: Context = BaseApplication.baseComponent.application.baseContext

    fun getExchangeRate() {
        progressVisibility.set(View.VISIBLE)

        val subscribe: Disposable = nbuExchangeRate.getExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result -> exchangeObserver.postValue(ResponseWrapper(filterResult(result))) },

                { error -> exchangeObserver.postValue(ResponseWrapper(error = error)) },

                { progressVisibility.set(View.GONE) })

        disposable.add(subscribe)
    }

    fun getPreviousExchangeRate() {
        val subscribe: Disposable = nbuExchangeRate.getExchangeByDateAndCurrency("usd", "20200525")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.d("TAG", "getExchangeRate: ${result[0]}")
                },

                { error ->
                    Log.d("TAG", "getExchangeRate: " + error.message)
                }
            )
        disposable.add(subscribe)
    }

    private fun filterResult(result: MutableList<Nbu>): List<Nbu> {
        return result.filter { nbu ->
            currencies.checkCurrencyExistingByAbr(nbu.currencyAbbreviation, context)
        }
    }
}