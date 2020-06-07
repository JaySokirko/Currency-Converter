package com.jay.currencyconverter.ui.calculatorActivity

import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.jay.currencyconverter.model.exchangeRate.currency.Currency
import com.jay.currencyconverter.util.CurrencyCalculator
import com.jay.currencyconverter.util.ObservableFieldWrapper
import com.jay.currencyconverter.util.letBlock
import com.jay.currencyconverter.util.removeLastChar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject

class CalculatorActivityViewModel : ViewModel(), LifecycleObserver{

    val baseCurrencyObserver: PublishSubject<Currency> = PublishSubject.create()
    val conversionCurrencyObserver: PublishSubject<Currency> = PublishSubject.create()
    val result: ObservableField<String> = ObservableField()
    val currencyCoefficient: ObservableField<String> = ObservableField()
    val inputValue: ObservableFieldWrapper<String> = ObservableFieldWrapper()
    val disposable = CompositeDisposable()
        //changes
    
    init {
        result.set("0")
        inputValue.setField("0")
        observeCurrenciesChoice()
        observeInput()
    }

    fun onOneClick() {
        setInputValue("1")
    }

    fun onTwoClick() {
        setInputValue("2")
    }

    fun onThreeClick() {
        setInputValue("3")
    }

    fun onFourClick() {
        setInputValue("4")
    }

    fun onFiveClick() {
        setInputValue("5")
    }

    fun onSixClick() {
        setInputValue("6")
    }

    fun onSevenClick() {
        setInputValue("7")
    }

    fun onEightClick() {
        setInputValue("8")
    }

    fun onNineClick() {
        setInputValue("9")
    }

    fun onZeroClick() {
        setInputValue("0")
    }

    fun onDotClick() {
        setInputValue(".")
    }

    fun onEraseClick() {
        erase()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposable.clear()
    }

    private fun observeInput() {
        disposable.add(inputValue.publishSubject.subscribe { value ->

            setupResult(inputValue = value)
        })
    }

    private fun observeCurrenciesChoice() {
        Observables.combineLatest(baseCurrencyObserver, conversionCurrencyObserver)
        { baseCurrency, conversionCurrency ->

            setupCurrencyCoefficient(baseCurrency.bid, conversionCurrency.bid)
            setupResult()

        }.subscribe()
    }

    private fun setInputValue(input: String) {
        val builder = StringBuilder()
        val current: String = inputValue.get().toString()
        val dot = "."

        if (current == "0" && input != dot) {
            inputValue.setField(input)
            return
        }

        if (current.contains(dot) && input == dot) {
            return
        }

        if (current.last().toString() == dot && input == dot) {
            return
        }

        //TODO implement correct
        if (current.length >= 12) {
            return

        } else {
            builder.append(current)
            builder.append(input)
            inputValue.setField(builder.toString())
        }
    }

    private fun erase() {
        val current: String = inputValue.get().toString()

        if (current.length == 1) {
            inputValue.set("0")

        } else {
            inputValue.setField(current.removeLastChar())
        }
    }

    private fun setupCurrencyCoefficient(baseCurrencyBid: String?, conversionCurrencyBid: String?) {
        letBlock(baseCurrencyBid, conversionCurrencyBid) { baseBid, conversionBid ->
            currencyCoefficient.set(
                CurrencyCalculator.calculateExchangeCoefficient(
                    baseBid.toDouble(), conversionBid.toDouble()
                ).toString()
            )
        }
    }

    private fun setupResult() {
        letBlock(currencyCoefficient.get(), inputValue.get()) { coefficient, value ->
            result.set(
                CurrencyCalculator.calculateExchangeRate(
                    coefficient.toDouble(),
                    value.toDouble()
                ).toString()
            )
        }
    }

    private fun setupResult(inputValue: String) {
        currencyCoefficient.get()?.let { coefficient ->
            result.set(
                CurrencyCalculator.calculateExchangeRate(
                    coefficient.toDouble(),
                    inputValue.toDouble()
                ).toString()
            )
        }
    }
}