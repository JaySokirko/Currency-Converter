package com.jay.currencyconverter.ui.nbuActivity

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import com.jay.currencyconverter.repository.NbuDatabaseManager
import com.jay.currencyconverter.util.TAG
import com.jay.currencyconverter.util.common.ConnectionErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainContentViewModel : BaseNbuViewModel() {

    val exchangeRateObserver: MutableLiveData<ResponseWrapper<Map<NbuCurrency, Boolean>>> = MutableLiveData()
    val actualDate: ObservableField<String> = ObservableField()
    val progressVisibility: ObservableInt = ObservableInt()

    private val nbuDataBaseManager: NbuDatabaseManager = NbuDatabaseManager.instance
    private var isFirstRequest: Boolean = true

    fun getExchangeRate() {
        progressVisibility.set(View.VISIBLE)
        Log.d(TAG, "getExchangeRate: ")

        val subscribe: Disposable = nbuExchangeRate.getExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { currencyList: MutableList<NbuCurrency> -> filterNotExistCurrency(currencyList) }
            .map { currencyList: MutableList<NbuCurrency> -> sortCurrenciesList(currencyList) }
            .subscribe(
                { result: List<NbuCurrency> -> onExchangeRateLoadFinished(result) },
                { error: Throwable -> onExchangeRateLoadError(error) })

        disposable.add(subscribe)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        Log.d(TAG, "onResume: ")
        onCurrenciesToDisplayPrepared()
    }

    private fun onExchangeRateLoadFinished(result: List<NbuCurrency>) {
        Log.d(TAG, "view model onExchangeRateLoadFinished: " + result.size)
        nbuDataBaseManager.putData(result)

        result.find { it.currencyAbbreviation == context.resources.getString(R.string.USD) }?.let {usdCurrency ->
            actualDate.set("${context.resources.getString(R.string.actual_date)} ${usdCurrency.exchangeDate}")
        }
    }

    private fun onCurrenciesToDisplayPrepared() {
        val subscribe: Disposable = nbuDataBaseManager.currenciesToDisplay
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {listToDisplay ->
                Log.d(TAG, "onCurrenciesToDisplayPrepared: " +  listToDisplay.size)
                exchangeRateObserver.postValue(ResponseWrapper(listToDisplay))
                progressVisibility.set(View.GONE)
            }

        disposable.add(subscribe)
    }

    private fun onExchangeRateLoadError(error: Throwable) {
        if (isFirstRequest) {
            ConnectionErrorHandler.onSslHandshakeAborted(error) {
                Log.d(TAG, "onExchangeRateLoadError: " + error.message)
                getExchangeRate()
                isFirstRequest = false
            }
        }
        else {
            exchangeRateObserver.postValue(ResponseWrapper(error = error))
        }
        Log.d(TAG, "onExchangeRateLoadError: " + error.message)
    }


}