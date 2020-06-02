package com.jay.currencyconverter.ui.nbuActivity

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.currency.Currencies
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.repository.NbuExchangeRate
import com.jay.currencyconverter.ui.BaseViewModel
import com.jay.currencyconverter.util.Constant.CURRENCY_ABR
import com.jay.currencyconverter.util.DateManager
import com.jay.currencyconverter.util.StorageManager
import com.jay.currencyconverter.util.TAG
import com.jay.currencyconverter.util.roundToFloat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NbuActivityViewModel : BaseViewModel() {

    val progressVisibility: ObservableInt = ObservableInt()
    val appBarProgressVisibility: ObservableInt = ObservableInt()
    val previousExchangeRateLoadErrorTitleVisibility: ObservableInt = ObservableInt()
    val previousExchangeRateLoadErrorButtonVisibility: ObservableInt = ObservableInt()
    val averagePreviousExchangeRateValue: ObservableField<String> = ObservableField()
    val currencyAbbreviation: ObservableField<String> = ObservableField()
    val exchangeRateObserver: MutableLiveData<ResponseWrapper<List<Nbu>>> = MutableLiveData()
    val previousExchangeRateObserver: MutableLiveData<ResponseWrapper<MutableList<Double>>> =
        MutableLiveData()

    private val currencies: Currencies = Currencies()
    private val nbuExchangeRate: NbuExchangeRate = NbuExchangeRate()
    private val context: Context = BaseApplication.baseComponent.application.baseContext
    private val listOfNbuLists: MutableList<ArrayList<Nbu>> = mutableListOf()
    private var isRequestRepeated: Boolean = false

    init {
        currencyAbbreviation.set(context.resources.getString(R.string.USD))
        appBarProgressVisibility.set(View.GONE)
        previousExchangeRateLoadErrorTitleVisibility.set(View.GONE)
        previousExchangeRateLoadErrorButtonVisibility.set(View.GONE)
    }

    fun onReloadBtnClick() {
        previousExchangeRateLoadErrorTitleVisibility.set(View.GONE)
        previousExchangeRateLoadErrorButtonVisibility.set(View.GONE)
        currencyAbbreviation.get()?.let { getPreviousExchangeRate(it) }
    }

    fun getExchangeRate() {
        progressVisibility.set(View.VISIBLE)

        val subscribe: Disposable = nbuExchangeRate.getExchangeRate()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result: MutableList<Nbu> ->
                    exchangeRateObserver.postValue(ResponseWrapper(filterNotExistCurrency(result)))
                },

                { error: Throwable -> onExchangeRateLoadError(error) },

                { progressVisibility.set(View.GONE) }
            )

        disposable.add(subscribe)
    }

    fun getPreviousExchangeRate(currencyAbbreviation: String) {
        appBarProgressVisibility.set(View.VISIBLE)
        this.currencyAbbreviation.set(currencyAbbreviation)
        StorageManager.saveVariable(CURRENCY_ABR, currencyAbbreviation)

        val zip: Observable<Unit> =
            Observable.zip(nbuExchangeRate.createRequestList(currencyAbbreviation, DateManager.dateList))
            { response ->
                response.forEach { list: Any ->
                    listOfNbuLists.add(list as ArrayList<Nbu>)
                }
            }

        val subscribe: Disposable = zip
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { onPreviousExchangeRateLoadFinish() },

                { onPreviousExchangeRateLoadError()
                    //todo remove
                    Log.d(TAG, "onPreviousExchangeLoadError: " + it.message) },

                { appBarProgressVisibility.set(View.GONE) }
            )

        disposable.add(subscribe)
    }

    private fun filterNotExistCurrency(list: MutableList<Nbu>): List<Nbu> {
        return list.filter { nbu ->
            currencies.checkCurrencyExistingByAbr(nbu.currencyAbbreviation, context)
        }
    }

    private fun extractExchangeRate(list: MutableList<ArrayList<Nbu>>): MutableList<Double> {
        val exchangeRateList: MutableList<Double> = mutableListOf()
        list.forEach { innerList -> innerList.forEach { nbu -> exchangeRateList.add(nbu.rate) } }
        return exchangeRateList
    }

    private fun onPreviousExchangeRateLoadFinish() {
        val exchangeRateList: MutableList<Double> = extractExchangeRate(listOfNbuLists)

        averagePreviousExchangeRateValue
            .set(calculateAverage(exchangeRateList).roundToFloat(3).toString())

        previousExchangeRateObserver.postValue(ResponseWrapper(exchangeRateList))
        listOfNbuLists.clear()
    }

    private fun onExchangeRateLoadError(error: Throwable) {
        if (!isRequestRepeated) {
            error.message?.let { errorMessage: String ->
                if (errorMessage.startsWith("SSL handshake aborted", ignoreCase = true)) {
                    isRequestRepeated = true
                    getExchangeRate()
                }
            }
        } else {
            exchangeRateObserver.postValue(ResponseWrapper(error = error))
        }
    }

    private fun onPreviousExchangeRateLoadError() {
        appBarProgressVisibility.set(View.GONE)
        previousExchangeRateLoadErrorTitleVisibility.set(View.VISIBLE)
        previousExchangeRateLoadErrorButtonVisibility.set(View.VISIBLE)
    }

    private fun calculateAverage(list: MutableList<Double>): Double{
        var sum = 0.0
        for (element: Double in list) {
            sum = sum.plus(element)
        }
        return sum.div(list.size)
    }
}