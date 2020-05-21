package com.jay.currencyconverter.ui.nbuActivity

import android.content.Context
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.currencyExchange.currency.Currencies
import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu
import com.jay.currencyconverter.ui.presenter.IExchangeRatePresenter
import com.jay.currencyconverter.ui.presenter.IPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NbuPresenter(private val view: INbuView, val context: Context) : IPresenter,
    IExchangeRatePresenter {

    private val disposable = CompositeDisposable()
    private val currencies: Currencies = Currencies()
    private val nbuExchangeRate: Observable<MutableList<Nbu>> =
        BaseApplication.getNetworkComponent().nbuExchange

    override fun getExchangeRate() {
        view.showProgress()

        disposable.add(
            nbuExchangeRate
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result -> view.onExchangeRateLoadFinish(filterResult(result)) },
                    { error -> view.onExchangeRateLoadError(error) },
                    { view.hideProgress() }
                )
        )
    }

    override fun onDestroy() {
        disposable.dispose()
        disposable.clear()
    }

    private fun filterResult(result: MutableList<Nbu>): List<Nbu> {
        return result.filter { nbu ->
            currencies.checkCurrencyExistingByAbr(nbu.currencyAbbreviation, context)
        }
    }

}