package com.jay.currencyconverter.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.util.CurrencyCalculator
import com.jay.currencyconverter.util.CustomObservableField
import com.jay.currencyconverter.util.removeLastChar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject

class CalculatorModel {

    val disposable = CompositeDisposable()
    val baseCurrencyObserver: PublishSubject<Currency> = PublishSubject.create()
    val conversionCurrencyObserver: PublishSubject<Currency> = PublishSubject.create()
    val result: ObservableField<String> = ObservableField()
    val currencyCoefficient: ObservableField<String> = ObservableField()
    val inputValue: CustomObservableField<String> = CustomObservableField()

    val tag = "TAG"

    init {
        result.set("0")
        inputValue.setField("0")
        observeCurrencies()
        observeInput()
    }

    fun onDestroy() {
        disposable.dispose()
        disposable.clear()
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

    private fun observeInput() {
        disposable.add(inputValue.publishSubject.subscribe { value ->
            result.set(
                CurrencyCalculator.calculate(
                    currencyCoefficient.get()!!.toDouble(),
                    value.toDouble()
                ).toString()
            )
        })
    }

    private fun observeCurrencies() {
        Observables.combineLatest(baseCurrencyObserver, conversionCurrencyObserver)
        { baseCurrency, conversionCurrency ->

            val baseCurrencyBid: Double = baseCurrency.bid!!.toDouble()
            val conversionCurrencyBid: Double = conversionCurrency.bid!!.toDouble()
            val coefficient: Double = baseCurrencyBid.div(conversionCurrencyBid)
            currencyCoefficient.set(coefficient.toString())

            result.set(
                CurrencyCalculator.calculate(
                    currencyCoefficient.get()!!.toDouble(),
                    inputValue.get()!!.toDouble()
                ).toString()
            )

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
            inputValue.setField(current.removeLastChar(current)!!)
        }
    }
}