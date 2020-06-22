package com.jay.currencyconverter.ui.calculatorActivity

import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.animation.TextSizeAnimation.getTextSizeInSP
import com.jay.currencyconverter.animation.TextSizeAnimation.setTextSizeWithAnimation
import com.jay.currencyconverter.databinding.ActivityCalculatorBinding
import com.jay.currencyconverter.di.calculatorActivity.DaggerCalculatorActivityComponent
import com.jay.currencyconverter.model.CurrencyChoice
import com.jay.currencyconverter.model.exchangeRate.Currencies
import com.jay.currencyconverter.model.exchangeRate.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.UAH
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganization
import com.jay.currencyconverter.model.exchangeRate.organization.NbuOrganization
import com.jay.currencyconverter.ui.BaseActivity
import com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter.HorizontalCurrencyButtonsAdapter
import com.jay.currencyconverter.util.common.Constant.CURRENCIES
import com.jay.currencyconverter.util.common.Constant.CURRENCIES_CHOSEN
import com.jay.currencyconverter.util.common.Constant.CURRENCIES_NOT_CHOSEN
import com.jay.currencyconverter.util.common.Constant.CALCULATOR_HINTS_NOT_SHOWN
import com.jay.currencyconverter.util.common.Constant.NBU_CURRENCIES
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION
import com.jay.currencyconverter.util.common.StorageManager
import com.jay.currencyconverter.util.ui.HintManager
import com.jay.currencyconverter.util.ui.LinearLayoutManagerWrapper
import com.jay.currencyconverter.util.ui.SmoothScroller
import kotlinx.android.synthetic.main.activity_calculator.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CalculatorActivity : BaseActivity() {

    @Inject
    lateinit var calculatorVM: CalculatorActivityViewModel

    @Inject
    lateinit var horizontalCurrencyAdapter: HorizontalCurrencyButtonsAdapter

    private val currenciesList: MutableList<Currency?> = mutableListOf()
    private val typedValue = TypedValue()
    private var isTextSizeIncreased = false

    private val linearLayoutManager =
            LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerCalculatorActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)

        initBinding()

        (intent.getParcelableExtra(ORGANIZATION) as CommonOrganization?)?.let { organization ->
            calculatorVM.organizationChoiceObserver.onNext(organization)
        }

        (intent.getParcelableExtra(CURRENCIES) as Currencies?)?.let { currencies: Currencies ->
            fillCurrenciesList(currencies)
        }

        intent.getParcelableArrayListExtra<Currency?>(NBU_CURRENCIES)?.let { currenciesList: ArrayList<Currency?> ->
            val nbuOrganization = NbuOrganization().apply { initTile(this@CalculatorActivity) }
            calculatorVM.organizationChoiceObserver.onNext(nbuOrganization)
            fillCurrenciesList(currenciesList)
        }

        calculatorVM.initHints(this)

        setupCurrencyButtonsList()
        onCurrencyButtonsAdapterItemClick()
        onEraseLongClick()
        showHints()
        observeCurrenciesChoice()
        onCurrenciesSearch()

        lifecycle.addObserver(calculatorVM)
        lifecycle.addObserver(horizontalCurrencyAdapter)
    }

    private fun initBinding() {
        val binding: ActivityCalculatorBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_calculator)
        binding.calculator = calculatorVM
    }

    private fun setupCurrencyButtonsList() {
        currencies_list.setHasFixedSize(true)
        currencies_list.layoutManager = linearLayoutManager
        currencies_list.adapter = horizontalCurrencyAdapter
        horizontalCurrencyAdapter.setItems(currenciesList)
    }

    private fun onCurrencyButtonsAdapterItemClick() {
        horizontalCurrencyAdapter.currencyButtonClickObserver.observe(this,
            Observer { choice: CurrencyChoice ->
            calculatorVM.currencyChoiceObserver.onNext(choice)
        })
    }

    private fun fillCurrenciesList(currencies: Currencies) {
        currenciesList.clear()
        currenciesList.add(UAH())
        currenciesList.addAll(currencies.getAllNotNullCurrencies())
    }

    private fun fillCurrenciesList(list: ArrayList<Currency?>) {
        currenciesList.clear()
        currenciesList.add(UAH())
        currenciesList.addAll(list.filterNotNull())
    }

    private fun onCurrenciesSearch() {
        RxTextView.textChanges(search_currency_edit_text)
                .skip(1)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map { it.toString().replace(oldValue = " ", newValue = "") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text: String ->
                    SmoothScroller.scrollToPosition(linearLayoutManager,
                            horizontalCurrencyAdapter.getItemPositionBySearch(text))
                }
    }

    private fun showHints() {
        if (StorageManager.getVariable(CALCULATOR_HINTS_NOT_SHOWN, true)) {
            val hintManager = HintManager()

            horizontalCurrencyAdapter.onAdapterViewsAttachedObserver.observe(this, Observer {

                val firstView: View? =
                    currencies_list.findViewHolderForAdapterPosition(0)
                        ?.itemView?.findViewById(R.id.base_currency_btn)

                val secondView: View? =
                    currencies_list.findViewHolderForAdapterPosition(1)
                        ?.itemView?.findViewById(R.id.conversion_currency_btn)

                val subscription = hintManager.showDelayedHint(1500, firstView,
                    getString(R.string.choose_base_currency_calculator_hint), onComplete = {

                    hintManager.showDelayedHint(delay = 0, targetView = secondView,
                        title = getString(R.string.choose_conversion_currency_hint), onComplete = {

                        hintManager.showDelayedHint(delay = 0, targetView = erase,
                            title = getString(R.string.erase_all_hint), onComplete = {

                                StorageManager.saveVariable(CALCULATOR_HINTS_NOT_SHOWN, false)
                        })
                    })
                })
                disposable.add(subscription)
            })
        }
    }

    private fun onEraseLongClick() {
        erase.setOnLongClickListener {
            calculatorVM.onLongEraseClick()
            return@setOnLongClickListener false
        }
    }

    private fun observeCurrenciesChoice() {
        val duration: Long = resources.getInteger(R.integer.durationX2).toLong()

        calculatorVM.onCurrenciesChosenObserver.observe(this, Observer {
            when (it) {
                CURRENCIES_CHOSEN -> {
                    calculator_header_transition.setTransition(R.id.items_transition)
                    calculator_header_transition.transitionToEnd()

                    Handler().postDelayed({
                        if (!isTextSizeIncreased) {
                            increaseTexViewSize(exchange_rate_title,
                                                exchange_rate,
                                                input_value_title,
                                                input_value,
                                                result_title,
                                                result)
                            isTextSizeIncreased = true
                        }
                    }, duration)
                }

                CURRENCIES_NOT_CHOSEN -> {
                    if (isTextSizeIncreased) {
                        reduceTextViewSize(exchange_rate_title,
                                           exchange_rate,
                                           input_value_title,
                                           input_value,
                                           result_title,
                                           result)
                        isTextSizeIncreased = false
                    }

                    Handler().postDelayed({
                        calculator_header_transition.setTransition(R.id.items_transition)
                        calculator_header_transition.transitionToStart()
                    }, duration)
                }
            }
        })
    }

    private fun increaseTexViewSize(vararg textView: TextView) {
        resources.getValue(R.dimen.text_increase_step, typedValue, true)

        textView.forEach { view: TextView ->
            val targetSize: Float = view.getTextSizeInSP() + typedValue.float
            view.setTextSizeWithAnimation(targetSize)
        }
    }

    private fun reduceTextViewSize(vararg textView: TextView) {
        resources.getValue(R.dimen.text_increase_step, typedValue, true)

        textView.forEach { view: TextView ->
            val targetSize: Float = view.getTextSizeInSP() - typedValue.float
            view.setTextSizeWithAnimation(targetSize)
        }
    }
}