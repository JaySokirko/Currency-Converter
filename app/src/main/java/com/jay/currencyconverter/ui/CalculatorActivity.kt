package com.jay.currencyconverter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityCalculatorBinding
import com.jay.currencyconverter.model.currencyExchange.Currencies
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.model.currencyExchange.currency.UAH
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.util.Constant
import com.jay.currencyconverter.viewModel.CalculatorModel
import kotlinx.android.synthetic.main.activity_calculator.*


class CalculatorActivity : AppCompatActivity() {

    private val currenciesList: MutableList<Currency?> = mutableListOf()
    private val baseCurrencyAdapter: CurrencyChoiceAdapter = CurrencyChoiceAdapter(this)
    private val conversionCurrencyAdapter: CurrencyChoiceAdapter = CurrencyChoiceAdapter(this)
    private var currencies: Currencies? = null
    private var organizationTitle: String? = null
    private val calculatorModel: CalculatorModel = CalculatorModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCalculatorBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_calculator)
        binding.calculator = calculatorModel

        organizationTitle = intent.getStringExtra(Constant.ORGANIZATION_NAME)
        currencies = intent.getParcelableExtra(Constant.CURRENCIES)

        fillCurrenciesList()

        setupRecyclerViews()

        baseCurrencyAdapter.setItems(currenciesList)
        conversionCurrencyAdapter.setItems(currenciesList)

        onAdapterItemClick()
    }

    override fun onDestroy() {
        calculatorModel.onDestroy()
        super.onDestroy()
    }

    private fun setupRecyclerViews() {
//        base_currencies_list.setHasFixedSize(true)
        base_currencies_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        base_currencies_list.adapter = baseCurrencyAdapter

//        conversion_currencies_list.setHasFixedSize(true)
        conversion_currencies_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        conversion_currencies_list.adapter = conversionCurrencyAdapter
    }

    private fun onAdapterItemClick() {
        calculatorModel.disposable.addAll(
            baseCurrencyAdapter.clickEvent.subscribe { helper: CurrencyChoiceAdapter.Helper ->
                conversionCurrencyAdapter.displayItemAtPositionByState(helper.selectedPosition!!, helper.state!!)
                calculatorModel.baseCurrencyObserver.onNext(helper.selectedCurrency!!)
            },
            conversionCurrencyAdapter.clickEvent.subscribe { helper: CurrencyChoiceAdapter.Helper ->
                baseCurrencyAdapter.displayItemAtPositionByState(helper.selectedPosition!!, helper.state!!)
                calculatorModel.conversionCurrencyObserver.onNext(helper.selectedCurrency!!)
            }
        )
    }

    private fun fillCurrenciesList() {
        currenciesList.clear()
        currenciesList.add(UAH())
        currenciesList.addAll(currencies?.allAvailableCurrencies!!)
    }
}