package com.jay.currencyconverter.ui.nbuActivity

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.repository.NbuDatabaseManager
import com.jay.currencyconverter.util.ConnectionErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainContentViewModel : BaseNbuViewModel() {

    val exchangeRateObserver: MutableLiveData<ResponseWrapper<Map<Nbu, Boolean>>> = MutableLiveData()
    val actualDate: ObservableField<String> = ObservableField()
    val progressVisibility: ObservableInt = ObservableInt()

    private val nbuDataBaseManager: NbuDatabaseManager = NbuDatabaseManager.instance
    private var isFirstRequest: Boolean = true

    init {
        onCurrenciesToDisplayPrepared()
    }

    fun getExchangeRate() {
        progressVisibility.set(View.VISIBLE)

        val subscribe: Disposable = nbuExchangeRate.getExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { currencyList: MutableList<Nbu> -> filterNotExistCurrency(currencyList) }
            .map { currencyList: MutableList<Nbu> -> sortCurrenciesList(currencyList) }
            .subscribe(
                { result: List<Nbu> -> onExchangeRateLoadFinished(result) },
                { error: Throwable -> onExchangeRateLoadError(error) }
            )

        disposable.add(subscribe)
    }

    private fun onExchangeRateLoadFinished(result: List<Nbu>) {
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
                exchangeRateObserver.postValue(ResponseWrapper(listToDisplay))
                progressVisibility.set(View.GONE)
            }

        disposable.add(subscribe)
    }

    private fun onExchangeRateLoadError(error: Throwable) {
        if (isFirstRequest) {
            ConnectionErrorHandler.onSslHandshakeAborted(error) {
                getExchangeRate()
                isFirstRequest = false
            }
        } else {
            exchangeRateObserver.postValue(ResponseWrapper(error = error))
        }
    }


}