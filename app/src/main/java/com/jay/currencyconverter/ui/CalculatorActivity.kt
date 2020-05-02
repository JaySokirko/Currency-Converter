package com.jay.currencyconverter.ui

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityCalculatorBinding
import com.jay.currencyconverter.model.currencyExchange.Currencies
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.model.currencyExchange.currency.CurrencyType
import com.jay.currencyconverter.model.currencyExchange.currency.UAH
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.util.Constant
import com.jay.currencyconverter.viewModel.CalculatorModel
import kotlinx.android.synthetic.main.activity_calculator.*


class CalculatorActivity : AppCompatActivity() {

    private val currenciesList: MutableList<Currency?> = mutableListOf()
    private val currencyChoiceAdapter: CurrencyChoiceAdapter = CurrencyChoiceAdapter()
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
        setupCurrencyChoiceList()
        onCurrencyChoiceListItemClick()
    }

    override fun onDestroy() {
        calculatorModel.onDestroy()
        super.onDestroy()
    }

    private fun setupCurrencyChoiceList() {
        base_currencies_list.setHasFixedSize(true)
        base_currencies_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        base_currencies_list.adapter = currencyChoiceAdapter
        currencyChoiceAdapter.setItems(currenciesList)
    }

    private fun onCurrencyChoiceListItemClick() {
        calculatorModel.disposable.add(
            currencyChoiceAdapter.clickEvent.subscribe { helper: CurrencyChoiceAdapter.Helper ->
                when(helper.currencyType){
                    CurrencyType.BASE -> {
                        calculatorModel.baseCurrencyObserver.onNext(helper.selectedCurrency!!)
                    }
                    CurrencyType.CONVERSION -> {
                        calculatorModel.conversionCurrencyObserver.onNext(helper.selectedCurrency!!)
                    }
                }
            }
        )
    }

    private fun fillCurrenciesList() {
        currenciesList.clear()
        currenciesList.add(UAH())
        currenciesList.addAll(currencies?.allAvailableCurrencies!!)
    }
}