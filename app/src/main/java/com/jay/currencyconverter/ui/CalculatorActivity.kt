package com.jay.currencyconverter.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityCalculatorBinding
import com.jay.currencyconverter.databinding.ActivityCalculatorBindingImpl
import com.jay.currencyconverter.model.Subject
import com.jay.currencyconverter.model.currencyExchange.Currencies
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.model.currencyExchange.currency.UAH
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.util.Constant
import com.jay.currencyconverter.viewModel.CalculatorModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_calculator.*


class CalculatorActivity : AppCompatActivity() {

    private val currenciesList: MutableList<Currency?> = mutableListOf()
    private val fromCurrencyAdapter: CurrencyChoiceAdapter = CurrencyChoiceAdapter(context = this)
    private val toCurrencyAdapter: CurrencyChoiceAdapter = CurrencyChoiceAdapter(context = this)
    private val disposable: CompositeDisposable = CompositeDisposable()
    private var currencies: Currencies? = null
    private var organizationTitle: String? = null
    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator)
        binding.calculator = CalculatorModel()

        organizationTitle = intent.getStringExtra(Constant.ORGANIZATION_NAME)
        currencies = intent.getParcelableExtra(Constant.CURRENCIES)

        fillCurrenciesList()

        setupRecyclerViews()

        fromCurrencyAdapter.setItems(currenciesList)
        toCurrencyAdapter.setItems(currenciesList)

        onAdapterItemClick()
    }

    override fun onDestroy() {
        disposable.dispose()
        disposable.clear()
        super.onDestroy()
    }

    private fun setupRecyclerViews() {
        from_currencies_list.setHasFixedSize(true)
        from_currencies_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        from_currencies_list.adapter = fromCurrencyAdapter

        to_currencies_list.setHasFixedSize(true)
        to_currencies_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        to_currencies_list.adapter = toCurrencyAdapter
    }

    private fun onAdapterItemClick() {
        disposable.addAll(
            fromCurrencyAdapter.clickEvent.subscribe { subject: Subject ->
                toCurrencyAdapter.hideItemAtPosition(subject.integerValue!!)
                binding.calculator!!.currencyFrom.set(subject.currency)
            },
            toCurrencyAdapter.clickEvent.subscribe { subject: Subject ->
                fromCurrencyAdapter.hideItemAtPosition(subject.integerValue!!)
                binding.calculator!!.currencyTo.set(subject.currency)
            }
        )
    }

    private fun fillCurrenciesList() {
        currenciesList.clear()
        currenciesList.add(UAH())
        currenciesList.addAll(currencies?.allAvailableCurrencies!!)
    }
}