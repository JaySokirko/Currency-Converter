package com.jay.currencyconverter.ui.calculatorActivity

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding.widget.RxTextView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.animation.TextSizeAnimation.getTextSizeInSP
import com.jay.currencyconverter.animation.TextSizeAnimation.setTextSizeWithAnimation
import com.jay.currencyconverter.databinding.ActivityCalculatorBinding
import com.jay.currencyconverter.di.DaggerCalculatorActivityComponent
import com.jay.currencyconverter.model.CurrencyChoice
import com.jay.currencyconverter.model.exchangeRate.Currencies
import com.jay.currencyconverter.model.exchangeRate.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.UAH
import com.jay.currencyconverter.model.exchangeRate.organization.Organization
import com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter.HorizontalCurrencyButtonsAdapter
import com.jay.currencyconverter.util.common.Constant.CURRENCIES
import com.jay.currencyconverter.util.common.Constant.CURRENCIES_CHOSEN
import com.jay.currencyconverter.util.common.Constant.CURRENCIES_NOT_CHOSEN
import com.jay.currencyconverter.util.common.Constant.ERASE_ALL_HINT_ALREADY_SHOWN
import com.jay.currencyconverter.util.common.Constant.ERASE_HINT_SHOULD_BE_SHOWN
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION
import com.jay.currencyconverter.util.common.StorageManager
import com.jay.currencyconverter.util.ui.LinearLayoutManagerWrapper
import com.jay.currencyconverter.util.ui.SmoothScroller
import kotlinx.android.synthetic.main.activity_calculator.*
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CalculatorActivity : AppCompatActivity() {

    @Inject
    lateinit var calculatorVM: CalculatorActivityViewModel

    @Inject
    lateinit var horizontalCurrencyAdapter: HorizontalCurrencyButtonsAdapter

    private  val linearLayoutManager =
        LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, false)

    private val currenciesList: MutableList<Currency?> = mutableListOf()
    private var isTextSizeIncreased = false

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerCalculatorActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)

        initBinding()

        (intent.getParcelableExtra(ORGANIZATION) as Organization?).let { organization ->
            organization?.let { calculatorVM.organizationChoiceObserver.onNext(it) }
        }

        fillCurrenciesList(intent.getParcelableExtra(CURRENCIES) as Currencies)
        setupCurrencyButtonsList()
        onCurrencyButtonsAdapterItemClick()
        onEraseLongClick()
        onShowEraseHint()
        observeCurrenciesChoice()
        onCurrenciesSearch()

        lifecycle.addObserver(calculatorVM)
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
        horizontalCurrencyAdapter.currencyButtonClick.observe(this, Observer { choice: CurrencyChoice ->
            calculatorVM.currencyChoiceObserver.onNext(choice)
        })
    }

    private fun fillCurrenciesList(currencies: Currencies?) {
        currenciesList.clear()
        currencies?.let {
            currenciesList.add(UAH())
            currenciesList.addAll(it.getAllNotNullCurrencies())
        }
    }

    private fun onCurrenciesSearch() {
        RxTextView.textChanges(search_currency_edit_text)
            .skip(1)
            .debounce(200, TimeUnit.MILLISECONDS)
            .map { it.toString().replace(oldValue = " ", newValue =  "") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {text: String ->
                smoothScrollToPosition(horizontalCurrencyAdapter.getItemPositionBySearch(text))
            }
    }

    private fun onShowEraseHint() {
        calculatorVM.eraseHintShouldBeShown.observe(this, Observer { hintShouldBeShown: Boolean ->

            if (hintShouldBeShown == ERASE_HINT_SHOULD_BE_SHOWN) {
                val snackBar = Snackbar.make(findViewById(android.R.id.content),
                                         resources.getString(R.string.erase_all_hint),
                                         Snackbar.LENGTH_INDEFINITE)

                snackBar.setAction(android.R.string.ok) {
                        StorageManager.saveVariable(ERASE_ALL_HINT_ALREADY_SHOWN, true)
                }.show()
            }
        })
    }

    private fun onEraseLongClick() {
        erase.setOnLongClickListener {
            calculatorVM.onLongEraseClick()
            return@setOnLongClickListener false
        }
    }

    private fun observeCurrenciesChoice() {
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
                    }, 200)
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
                    }, 200)
                }
            }
        })
    }

    private fun increaseTexViewSize(vararg textView: TextView) {
        textView.forEach { view: TextView ->
            val targetSize: Float = view.getTextSizeInSP() + 2.5f
            view.setTextSizeWithAnimation(targetSize)
        }
    }

    private fun reduceTextViewSize(vararg textView: TextView) {
        textView.forEach {view: TextView ->
            val targetSize: Float = view.getTextSizeInSP() - 2.5f
            view.setTextSizeWithAnimation(targetSize)
        }
    }

    private fun smoothScrollToPosition(position: Int) {
        SmoothScroller.getSmoothScroller().targetPosition = position
        linearLayoutManager.startSmoothScroll(SmoothScroller.getSmoothScroller());
    }

}