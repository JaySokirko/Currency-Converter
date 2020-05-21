package com.jay.currencyconverter.ui.bankActivity

import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.currencyExchange.bank.Banks
import com.jay.currencyconverter.ui.presenter.IExchangeRatePresenter
import com.jay.currencyconverter.ui.presenter.IPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BankPresenter(private val view: IBankView) : IPresenter, IExchangeRatePresenter {

    private val disposable = CompositeDisposable()
    private val bankExchangeRate: Observable<Banks?> =
        BaseApplication.getNetworkComponent().bankExchange

    override fun getExchangeRate() {
        view.showProgress()

        disposable.add(
            bankExchangeRate
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
    }
}