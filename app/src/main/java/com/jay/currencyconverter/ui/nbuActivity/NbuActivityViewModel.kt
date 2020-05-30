package com.jay.currencyconverter.ui.nbuActivity

import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.currency.Currencies
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.repository.NbuExchangeRate
import com.jay.currencyconverter.ui.BaseViewModel
import com.jay.currencyconverter.util.DateManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NbuActivityViewModel : BaseViewModel() {

    val progressVisibility: ObservableInt = ObservableInt()
    val appBarProgressVisibility: ObservableInt = ObservableInt()
    val previousExchangeRateLoadErrorTitleVisibility: ObservableInt = ObservableInt()
    val previousExchangeRateLoadErrorButtonVisibility: ObservableInt = ObservableInt()
    val exchangeRateObserver: MutableLiveData<ResponseWrapper<List<Nbu>>> = MutableLiveData()
    val previousExchangeRateObserver: MutableLiveData<ResponseWrapper<MutableList<Double>>> =
        MutableLiveData()

    private val currencies: Currencies = Currencies()
    private val nbuExchangeRate: NbuExchangeRate = NbuExchangeRate()
    private val context: Context = BaseApplication.baseComponent.application.baseContext
    private val listOfNbuLists: MutableList<ArrayList<Nbu>> = mutableListOf()

    init {
        appBarProgressVisibility.set(View.GONE)
        previousExchangeRateLoadErrorTitleVisibility.set(View.GONE)
        previousExchangeRateLoadErrorButtonVisibility.set(View.GONE)
    }

    fun onReloadBtnClick() {
        previousExchangeRateLoadErrorTitleVisibility.set(View.GONE)
        previousExchangeRateLoadErrorButtonVisibility.set(View.GONE)
        getPreviousExchangeRate()
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

                { error: Throwable -> exchangeRateObserver.postValue(ResponseWrapper(error = error)) },

                { progressVisibility.set(View.GONE) }
            )

        disposable.add(subscribe)
    }

    fun getPreviousExchangeRate() {
        appBarProgressVisibility.set(View.VISIBLE)

        val zip: Observable<Unit> =
            Observable.zip(nbuExchangeRate.createRequestList("usd", DateManager.dateList))
            { response ->
                response.forEach { list: Any ->
                    listOfNbuLists.add(list as ArrayList<Nbu>)
                }
            }

        val subscribe: Disposable = zip
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { previousExchangeRateObserver
                        .postValue(ResponseWrapper(extractExchangeRate(listOfNbuLists))) },

                { onPreviousExchangeLoadError() },

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

    private fun onPreviousExchangeLoadError() {
        appBarProgressVisibility.set(View.GONE)
        previousExchangeRateLoadErrorTitleVisibility.set(View.VISIBLE)
        previousExchangeRateLoadErrorButtonVisibility.set(View.VISIBLE)
    }
}