package com.jay.currencyconverter.ui.bankActivity

import com.jay.currencyconverter.model.currencyExchange.Banks
import com.jay.currencyconverter.service.CurrencyExchangeRateApi
import com.jay.currencyconverter.ui.presenter.IExchangeRatePresenter
import com.jay.currencyconverter.ui.presenter.IPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BankPresenter(private val view: IBankView) : IPresenter, IExchangeRatePresenter {

    private val disposable = CompositeDisposable()

    override fun getExchangeRate() {
        view.showProgress()
        disposable.add(
            CurrencyExchangeRateApi.create(CurrencyExchangeRateApi.BANKS_EXCHANGE_URL)
                .getBanksExchangeRate()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result: Banks? -> view.onExchangeRateLoadFinish(result?.organizations!!) },

                    { error: Throwable -> view.onExchangeRateLoadError(error) },

                    { view.hideProgress() }
                )
        )
    }

    override fun onDestroy() {
        disposable.dispose()
        disposable.clear()
        view.onDestroy()
    }
}