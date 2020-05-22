package com.jay.currencyconverter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityCalculatorBinding
import com.jay.currencyconverter.di.DaggerCalculatorActivityComponent
import com.jay.currencyconverter.model.currencyExchange.currency.Currencies
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.model.currencyExchange.currency.CurrencyType
import com.jay.currencyconverter.model.currencyExchange.currency.UAH
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.util.Constant
import com.jay.currencyconverter.viewModel.CalculatorActivityVM
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_calculator.*
import javax.inject.Inject


class CalculatorActivity : AppCompatActivity() {

    private val currenciesList: MutableList<Currency?> = mutableListOf()
    private var currencies: Currencies? = null
    private var organizationTitle: String? = null

    @Inject
    lateinit var calculatorVM: CalculatorActivityVM

    @Inject
    lateinit var currencyChoiceAdapter: CurrencyChoiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerCalculatorActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)

        initBinding()

        //TODO add organization title to calculator screen
        organizationTitle = intent.getStringExtra(Constant.ORGANIZATION_TITLE)
        currencies = intent.getParcelableExtra(Constant.CURRENCIES)

        fillCurrenciesList()
        setupCurrencyChoiceList()
        onCurrencyChoiceListItemClick()
    }

    override fun onDestroy() {
        calculatorVM.onDestroy()
        super.onDestroy()
    }

    private fun initBinding() {
        val binding: ActivityCalculatorBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_calculator)
        binding.calculator = calculatorVM
    }

    private fun setupCurrencyChoiceList() {
        base_currencies_list.setHasFixedSize(true)
        base_currencies_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        base_currencies_list.adapter = currencyChoiceAdapter
        currencyChoiceAdapter.setItems(currenciesList)
    }

    private fun onCurrencyChoiceListItemClick() {
        val subscribe: Disposable =
            currencyChoiceAdapter.clickEvent.subscribe { helper: CurrencyChoiceAdapter.Helper ->
                when (helper.currencyType) {
                    CurrencyType.BASE -> {
                        helper.selectedCurrency?.let {
                            calculatorVM.baseCurrencyObserver.onNext(it)
                        }
                    }
                    CurrencyType.CONVERSION -> {
                        helper.selectedCurrency?.let {
                            calculatorVM.conversionCurrencyObserver.onNext(it)
                        }
                    }
                    null -> {
                        throw NullPointerException("currencyType should not be null")
                    }
                }
            }

        calculatorVM.disposable.add(subscribe)
    }

    private fun fillCurrenciesList() {
        currenciesList.clear()
        currencies?.let {
            currenciesList.add(UAH())
            currenciesList.addAll(it.getAllNotNullCurrencies())
        }
    }
}