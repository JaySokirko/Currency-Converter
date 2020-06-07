package com.jay.currencyconverter.ui.nbuActivity

import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.util.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AppbarViewModel: BaseNbuViewModel() {

    val chartExchangeRate: MutableLiveData<ResponseWrapper<MutableList<Double>>> = MutableLiveData()
    val chartErrorTitleVisibility: ObservableInt = ObservableInt()
    val chartErrorButtonVisibility: ObservableInt = ObservableInt()
    val averageExchangeRateLayoutVisibility: ObservableInt = ObservableInt()
    val averageExchangeRateValue: ObservableField<String> = ObservableField()
    val appBarProgressVisibility: ObservableInt = ObservableInt()
    val isMainCheckboxChecked: ObservableBoolean = ObservableBoolean()
    val currencyAbbreviation: ObservableField<String> = ObservableField()

    private val listOfNbuLists: MutableList<ArrayList<Nbu>> = mutableListOf()
    private var isFirstRequest: Boolean = true
    private val defaultAbbr: String = context.resources.getString(R.string.USD)

    init {
        currencyAbbreviation.set(defaultAbbr)
        isMainCheckboxChecked.set(StorageManager.getVariable(Constant.MAIN_CHECKBOX_CHECKED, default = true))
        appBarProgressVisibility.set(View.GONE)
        chartErrorTitleVisibility.set(View.GONE)
        chartErrorButtonVisibility.set(View.GONE)
    }

    fun onReloadBtnClick() {
        averageExchangeRateLayoutVisibility.set(View.VISIBLE)
        chartErrorTitleVisibility.set(View.GONE)
        chartErrorButtonVisibility.set(View.GONE)
        getChartExchangeRate(defaultAbbr)
    }

    fun getChartExchangeRate(currencyAbbreviation: String) {
        appBarProgressVisibility.set(View.VISIBLE)
        this.currencyAbbreviation.set(currencyAbbreviation)
        StorageManager.saveVariable(Constant.CURRENCY_ABR, currencyAbbreviation)

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
                { onChartExchangeRateLoadFinish() },

                { error: Throwable -> onChartExchangeRateLoadError(error) },

                { appBarProgressVisibility.set(View.GONE) }
            )

        disposable.add(subscribe)
    }

    private fun onChartExchangeRateLoadFinish() {
        val exchangeRateList: MutableList<Double> = extractChartExchangeRate(listOfNbuLists)
        averageExchangeRateValue.set(calculateAverage(exchangeRateList).roundToFloat(4).toString())
        chartExchangeRate.postValue(ResponseWrapper(exchangeRateList))
        listOfNbuLists.clear()
    }

    private fun onChartExchangeRateLoadError(error: Throwable) {
        appBarProgressVisibility.set(View.GONE)
        averageExchangeRateLayoutVisibility.set(View.GONE)
        chartErrorTitleVisibility.set(View.VISIBLE)
        chartErrorButtonVisibility.set(View.VISIBLE)

        if (isFirstRequest) {
            ConnectionErrorHandler.onSslHandshakeAborted(error) {
                getChartExchangeRate(defaultAbbr)
                isFirstRequest = false
            }
        } else {
            chartExchangeRate.postValue(ResponseWrapper(error = error))
        }
        //TODO remove
        Log.d(TAG, "getPreviousExchangeRate: " + error.message)
    }

    private fun extractChartExchangeRate(list: MutableList<ArrayList<Nbu>>): MutableList<Double> {
        val exchangeRateList: MutableList<Double> = mutableListOf()
        list.forEach { innerList -> innerList.forEach { nbu -> exchangeRateList.add(nbu.rate) } }
        return exchangeRateList
    }

    private fun calculateAverage(list: MutableList<Double>): Double{
        var sum = 0.0
        for (element: Double in list) {
            sum = sum.plus(element)
        }
        return sum.div(list.size)
    }
}