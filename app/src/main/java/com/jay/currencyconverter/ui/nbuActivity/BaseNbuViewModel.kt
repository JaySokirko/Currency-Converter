package com.jay.currencyconverter.ui.nbuActivity

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.currency.Currencies
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.repository.exchangeRate.NbuExchangeRate
import io.reactivex.disposables.CompositeDisposable

open class BaseNbuViewModel : ViewModel(), LifecycleObserver {

    val nbuExchangeRate: NbuExchangeRate = NbuExchangeRate()
    val currencies: Currencies = Currencies()
    val context: Context = BaseApplication.baseComponent.application.baseContext
    val disposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposable.clear()
    }

    protected fun filterNotExistCurrency(list: MutableList<Nbu>): MutableList<Nbu> {
        return list.filter { nbu ->
            currencies.checkCurrencyExistingByAbr(nbu.currencyAbbreviation, context)
        } as MutableList<Nbu>
    }

    protected fun sortCurrenciesList(list: MutableList<Nbu>): MutableList<Nbu> {
        val usd: Nbu? = list.find {
            it.currencyAbbreviation == context.resources.getString(R.string.USD)
        }
        val eur: Nbu? = list.find {
            it.currencyAbbreviation == context.resources.getString(R.string.EUR)
        }
        val rub: Nbu? = list.find {
            it.currencyAbbreviation == context.resources.getString(R.string.RUB)
        }
        list.removeAll { it == usd || it == eur || it == rub }
        usd?.let { list.add(0, it) }
        eur?.let { list.add(1, it) }
        rub?.let { list.add(2, it) }

        return list
    }
}