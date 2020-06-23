package com.jay.currencyconverter.ui.calculatorActivity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.CurrencyChoice
import com.jay.currencyconverter.model.exchangeRate.Currency
import com.jay.currencyconverter.model.exchangeRate.CurrencyType
import com.jay.currencyconverter.model.exchangeRate.organization.Organization
import com.jay.currencyconverter.util.common.Constant.CANCEL_BASE_CURRENCY_CHOICE
import com.jay.currencyconverter.util.common.Constant.CANCEL_CONVERSION_CURRENCY_CHOICE
import com.jay.currencyconverter.util.common.Constant.CURRENCIES_CHOSEN
import com.jay.currencyconverter.util.common.Constant.CURRENCIES_NOT_CHOSEN
import com.jay.currencyconverter.util.common.CurrencyCalculator
import com.jay.currencyconverter.util.common.NumberFormatter
import com.jay.currencyconverter.util.common.ObservableFieldWrapper
import com.jay.currencyconverter.util.common.ValueChangeListener
import com.jay.currencyconverter.util.executeIfNotNull
import com.jay.currencyconverter.util.removeLastChar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.math.BigDecimal



class CalculatorActivityViewModel : ViewModel(), LifecycleObserver,
    ValueChangeListener.OnValueChangeListener<Currency> {

    val currencyChoiceObserver: PublishSubject<CurrencyChoice> = PublishSubject.create()
    val organizationChoiceObserver: PublishSubject<Organization> = PublishSubject.create()
    val onCurrenciesChosenObserver: MutableLiveData<Int> = MutableLiveData()
    val result: ObservableField<String> = ObservableField()
    val exchangeRateText: ObservableField<String> = ObservableField()
    val enteredValue: ObservableFieldWrapper<String> = ObservableFieldWrapper()
    val phoneCallBtnVisibility: ObservableInt = ObservableInt()
    val isBaseCurrencyCancelBtnVisible: ObservableBoolean = ObservableBoolean()
    val isConversionCurrencyCancelBtnVisible: ObservableBoolean = ObservableBoolean()
    val phoneCallBtnText: ObservableField<String> = ObservableField()
    val organizationTitleText: ObservableField<String> = ObservableField()
    val isCurrenciesChosen: ObservableBoolean = ObservableBoolean()
    val baseCurrencyCancelBtnText: ObservableField<String> = ObservableField()
    val conversionCurrencyCancelBtnText: ObservableField<String> = ObservableField()
    val baseCurrencyCancelBtnIcon: ObservableField<Drawable> = ObservableField()
    val conversionCurrencyCancelBtnIcon: ObservableField<Drawable> = ObservableField()

    private val context: Context = BaseApplication.baseComponent.application.applicationContext
    private val disposable = CompositeDisposable()
    private val baseCurrencyChangeListener: ValueChangeListener<Currency> = ValueChangeListener()
    private val conversionCurrencyChangeListener: ValueChangeListener<Currency> = ValueChangeListener()
    private var choseBaseCurrencyHint: String =
        context.resources.getString(R.string.choose_base_currency_hint)
    private var choseConversionCurrencyHint: String =
        context.resources.getString(R.string.choose_conversion_currency_hint)

    init {

        baseCurrencyChangeListener.setListener(this)
        conversionCurrencyChangeListener.setListener(this)

        exchangeRateText.set(choseBaseCurrencyHint)
        phoneCallBtnVisibility.set(View.GONE)
        isCurrenciesChosen.set(false)
        result.set("0")
        enteredValue.setField("0")

        observeCurrenciesChoice()
        observeInput()
        observeOrganization()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onActivityStopped() {
        disposable.clear()
    }

    fun initHints(context: Context){
        choseBaseCurrencyHint = context.getString(R.string.choose_base_currency_hint)
        choseConversionCurrencyHint = context.getString(R.string.choose_conversion_currency_hint)
        exchangeRateText.set(choseBaseCurrencyHint)
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

    fun onLongEraseClick() {
        eraseAll()
    }

    fun onPhoneCallClick() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneCallBtnText.get()))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    fun onBaseCurrencyCancelClick() {
        cancelBtnClickObserver.onNext(CANCEL_BASE_CURRENCY_CHOICE)
        isBaseCurrencyCancelBtnVisible.set(false)
        baseCurrencyChangeListener.value = null
    }

    fun onConversionCurrencyCancelClick() {
        cancelBtnClickObserver.onNext(CANCEL_CONVERSION_CURRENCY_CHOICE)
        isConversionCurrencyCancelBtnVisible.set(false)
        conversionCurrencyChangeListener.value = null
    }

    /**@see ValueChangeListener.OnValueChangeListener.onChange*/
    override fun onChange() {
        isCurrenciesChosen.set(false)

        if (baseCurrencyChangeListener.value != null || conversionCurrencyChangeListener.value != null) {
            onCurrenciesChosenObserver.postValue(CURRENCIES_CHOSEN)
        } else {
            onCurrenciesChosenObserver.postValue(CURRENCIES_NOT_CHOSEN)
        }

        if (baseCurrencyChangeListener.value == null) {
            isBaseCurrencyCancelBtnVisible.set(false)
        } else {
            isBaseCurrencyCancelBtnVisible.set(true)
            baseCurrencyCancelBtnText.set(baseCurrencyChangeListener.value?.getAbr(context))
            baseCurrencyCancelBtnIcon.set(baseCurrencyChangeListener.value?.getImage(context))
        }

        if (conversionCurrencyChangeListener.value == null) {
            isConversionCurrencyCancelBtnVisible.set(false)
        } else {
            isConversionCurrencyCancelBtnVisible.set(true)
            conversionCurrencyCancelBtnText.set(conversionCurrencyChangeListener.value?.getAbr(context))
            conversionCurrencyCancelBtnIcon.set(conversionCurrencyChangeListener.value?.getImage(context))
        }

        executeIfNotNull(baseCurrencyChangeListener.value, conversionCurrencyChangeListener.value)
        { baseCurrency, conversionCurrency ->
            isCurrenciesChosen.set(true)
            setupExchangeRate(baseCurrency.rate.toString(), conversionCurrency.rate.toString())
            calculateResult()
        }
    }

    private fun observeInput() {
        disposable.add(enteredValue.publishSubject.subscribe { enteredValue: String ->
            calculateResult(enteredValue)
        })
    }

    private fun observeCurrenciesChoice() {
        val subscribe: Disposable = currencyChoiceObserver
            .subscribe { currencyChoice: CurrencyChoice ->

                currencyChoice.chosenCurrency ?: return@subscribe
                currencyChoice.isSelected ?: return@subscribe
                currencyChoice.currencyType ?: return@subscribe

                if (currencyChoice.currencyType == CurrencyType.BASE && currencyChoice.isSelected) {
                    exchangeRateText.set(choseConversionCurrencyHint)
                    baseCurrencyChangeListener.value = currencyChoice.chosenCurrency
                } else

                if (currencyChoice.currencyType == CurrencyType.BASE && !currencyChoice.isSelected) {
                    exchangeRateText.set(choseBaseCurrencyHint)
                    baseCurrencyChangeListener.value = null
                    conversionCurrencyChangeListener.value = null
                } else

                if (currencyChoice.currencyType == CurrencyType.CONVERSION && !currencyChoice.isSelected) {
                    exchangeRateText.set(choseConversionCurrencyHint)
                    conversionCurrencyChangeListener.value = null
                } else

                if (currencyChoice.currencyType == CurrencyType.CONVERSION && currencyChoice.isSelected) {
                    conversionCurrencyChangeListener.value = currencyChoice.chosenCurrency
                }
            }

        disposable.add(subscribe)
    }

    private fun observeOrganization() {
        val subscribe: Disposable = organizationChoiceObserver
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { organization ->
                phoneCallBtnVisibility.set(View.VISIBLE)
                phoneCallBtnText.set(organization.phone)
                organizationTitleText.set(organization.title)
            }

        disposable.add(subscribe)
    }

    private fun setInputValue(newValue: String) {
        val builder = StringBuilder()
        val existValue: String = enteredValue.get().toString()
        val dot = "."

        if (existValue == "0" && newValue != dot) {
            enteredValue.setField(NumberFormatter.format(newValue))
            return
        }

        if (existValue.contains(dot) && newValue == dot) {
            return
        }

        if (existValue.last().toString() == dot && newValue == dot) {
            return
        }

        if  (existValue.length > context.resources.getInteger(R.integer.calculator_max_input_values)) {
            return
        }

        builder.append(existValue)
        builder.append(newValue)
        enteredValue.setField(NumberFormatter.format(builder.toString()))
    }

    private fun erase() {
        var current: String = enteredValue.get().toString()

        if (current.length == 1) {
            enteredValue.set("0")
        }
        else {
            current = current.removeLastChar()

            if (current.last().toString() == " "){
                current = current.removeLastChar()
                enteredValue.setField(NumberFormatter.format(current))
            } else {
                enteredValue.setField(NumberFormatter.format(current))
            }
        }

        calculateResult()
    }

    private fun eraseAll() {
        enteredValue.set("0")
        calculateResult()
    }

    private fun setupExchangeRate(baseCurrencyRate: String?, conversionCurrencyRate: String?) {
        executeIfNotNull(baseCurrencyRate, conversionCurrencyRate) { baseRate, conversionRate ->

            exchangeRateText.set(CurrencyCalculator.calculateExchangeRate(
                baseRate.toBigDecimal(),
                conversionRate.toBigDecimal()).toString()
            )
        }
    }

    private fun calculateResult() {
        executeIfNotNull(exchangeRateText.get(), enteredValue.get()) { exchangeRate, enteredValue ->

            if (!isCurrenciesChosen.get()) return

            val calculatedValue: BigDecimal =
                CurrencyCalculator.calculateResult(exchangeRate.toBigDecimal(),
                                                   reformatToBigDecimal(enteredValue))

            result.set(NumberFormatter.format(calculatedValue.toString()))
        }
    }

    private fun calculateResult(enteredValue: String) {
        if (!isCurrenciesChosen.get()) return

        exchangeRateText.get()?.let { exchangeRate ->

            val calculatedValue: BigDecimal =
                CurrencyCalculator.calculateResult(exchangeRate.toBigDecimal(),
                                                   reformatToBigDecimal(enteredValue))

            result.set(NumberFormatter.format(calculatedValue.toString()))
        }
    }

    private fun reformatToBigDecimal(value: String): BigDecimal {
         return value
             .replace("\\s".toRegex(), "")
             .toBigDecimal()
    }

    companion object {
        val cancelBtnClickObserver: PublishSubject<Int> = PublishSubject.create()
    }
}