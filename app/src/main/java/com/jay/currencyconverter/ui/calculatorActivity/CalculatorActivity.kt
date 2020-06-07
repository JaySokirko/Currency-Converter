package com.jay.currencyconverter.ui.calculatorActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityCalculatorBinding
import com.jay.currencyconverter.di.DaggerCalculatorActivityComponent
import com.jay.currencyconverter.model.CurrencyChoice
import com.jay.currencyconverter.model.exchangeRate.currency.Currencies
import com.jay.currencyconverter.model.exchangeRate.currency.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.CurrencyType
import com.jay.currencyconverter.model.exchangeRate.currency.UAH
import com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter.HorizontalCurrencyButtonsAdapter
import com.jay.currencyconverter.util.Constant
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_calculator.*
import javax.inject.Inject


class CalculatorActivity : AppCompatActivity() {

    private val currenciesList: MutableList<Currency?> = mutableListOf()
    private var currencies: Currencies? = null
    private var organizationTitle: String? = null

    @Inject
    lateinit var calculatorVM: CalculatorActivityViewModel

    @Inject
    lateinit var horizontalCurrencyAdapter: HorizontalCurrencyButtonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerCalculatorActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)

        initBinding()

        //TODO add organization title to calculator screen
        organizationTitle = intent.getStringExtra(Constant.ORGANIZATION_TITLE)
        currencies = intent.getParcelableExtra(Constant.CURRENCIES)

        fillCurrenciesList()
        setupCurrencyChoiceList()
        onCurrencyToConvertChosen()

        lifecycle.addObserver(calculatorVM)
    }

    private fun initBinding() {
        val binding: ActivityCalculatorBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_calculator)
        binding.calculator = calculatorVM
    }

    private fun setupCurrencyChoiceList() {
        base_currencies_list.setHasFixedSize(true)
        base_currencies_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        base_currencies_list.adapter = horizontalCurrencyAdapter
        horizontalCurrencyAdapter.setItems(currenciesList)
    }

    private fun onCurrencyToConvertChosen() {
        val subscribe: Disposable =
            horizontalCurrencyAdapter.clickEvent.subscribe { wrapper: CurrencyChoice ->
                when (wrapper.currencyType) {
                    CurrencyType.BASE -> {
                        wrapper.chosenCurrency?.let {
                            calculatorVM.baseCurrencyObserver.onNext(it)
                        }
                    }
                    CurrencyType.CONVERSION -> {
                        wrapper.chosenCurrency?.let {
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