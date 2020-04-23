package com.jay.currencyconverter.viewModel

import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.util.CurrencyCalculator
import java.lang.StringBuilder

class CalculatorModel {

    var result: ObservableDouble = ObservableDouble()
    var inputValue: ObservableField<String> = ObservableField()
    var currencyFrom: ObservableField<Currency> = ObservableField()
    var currencyTo: ObservableField<Currency> = ObservableField()

    init {
        result.set(0.0)
        inputValue.set("0")
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

    fun onEraseClick() {}

    fun onDotClick() {
        setInputValue(".")
    }

    private fun setInputValue(input: String) {
        val builder = StringBuilder()
        val current: String = inputValue.get().toString()
        val dot = "."

        if (current == "0.0" || current == "0" && input != dot) {
            inputValue.set(input)
        }

        if (current.contains(dot) && input == dot){
            return
        }

        if (current.last().toString() == dot && input == dot){
            return
        }

        else {
            builder.append(inputValue.get())
            builder.append(input)
            inputValue.set(builder.toString())
        }
    }
}