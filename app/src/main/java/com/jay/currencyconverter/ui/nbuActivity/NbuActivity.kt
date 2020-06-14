package com.jay.currencyconverter.ui.nbuActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.jay.currencyconverter.R
import com.jay.currencyconverter.animation.LayoutParamsAnimation
import com.jay.currencyconverter.databinding.ActivityNbuBinding
import com.jay.currencyconverter.di.nbuActivity.DaggerNbuActivityComponent
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import com.jay.currencyconverter.repository.NbuDatabaseManager
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.ui.adapter.ChartCurrencyAdapter
import com.jay.currencyconverter.ui.adapter.DisplayedCurrenciesAdapter
import com.jay.currencyconverter.ui.adapter.NbuExchangeRateAdapter
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivity
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.ui.dialog.NoCurrencyChosenDialog
import com.jay.currencyconverter.util.common.Constant.CURRENCY_ABR
import com.jay.currencyconverter.util.common.Constant.CURRENCY_ABR_DEFAULT
import com.jay.currencyconverter.util.common.Constant.EMPTY_STRING
import com.jay.currencyconverter.util.common.Constant.MAIN_CHECKBOX_CHECKED
import com.jay.currencyconverter.util.common.Constant.NBU_CURRENCIES
import com.jay.currencyconverter.util.common.StorageManager
import com.jay.currencyconverter.util.ui.LineChartSettings
import com.jay.currencyconverter.util.ui.RecyclerViewTouchItemListener
import kotlinx.android.synthetic.main.activity_nbu.*
import kotlinx.android.synthetic.main.layout_currency_settings.*
import javax.inject.Inject


class NbuActivity : NavigationActivity(), ErrorDialog.OnDialogButtonsClickListener {

    @Inject
    lateinit var nbuExchangeAdapter: NbuExchangeRateAdapter

    @Inject
    lateinit var chartCurrenciesAdapter: ChartCurrencyAdapter

    @Inject
    lateinit var displayCurrenciesAdapter: DisplayedCurrenciesAdapter

    @Inject
    lateinit var mainContentVM: MainContentViewModel

    @Inject
    lateinit var appbarViewModel: AppbarViewModel

    @Inject
    lateinit var errorDialog: ErrorDialog

    @Inject
    lateinit var layoutParamsAnimation: LayoutParamsAnimation

    @Inject
    lateinit var noCurrencyChosenDialog: NoCurrencyChosenDialog

    private val nbuCurrency = NbuCurrency()
    private var nbuDatabaseManager: NbuDatabaseManager = NbuDatabaseManager.instance
    private var isChartSettingsClosed = true
    private var isCurrenciesSettingsClosed = true
    private var listAppearDuration = 0L
    private lateinit var currenciesChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNbuActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_nbu, R.layout.default_toolbar)

        initBinding()

        setupNbuExchangeList()
        setupChartCurrenciesList()
        setupDisplayedCurrenciesList()
        onExchangeRateLoadFinish()
        onChartExchangeRateLoadFinish()
        onChartSettingsClick()
        onChartCurrencyChosen()
        onCurrenciesToDisplaySettingsClick()
        onMainCheckboxClick()
        onOpenCalculatorClick()

        currenciesChart = findViewById(R.id.line_chart)
        currenciesChart.setNoDataText(EMPTY_STRING)

        errorDialog.setOnDialogButtonsClickListener(this)

        mainContentVM.getExchangeRate()
        appbarViewModel.getChartExchangeRate(StorageManager.getVariable(CURRENCY_ABR, CURRENCY_ABR_DEFAULT))

        listAppearDuration = resources.getInteger(R.integer.durationX2).toLong()

        lifecycle.addObserver(displayCurrenciesAdapter)
        lifecycle.addObserver(mainContentVM)
        lifecycle.addObserver(nbuDatabaseManager)
    }

    /**@see ErrorDialog.OnDialogButtonsClickListener.onReload*/
    override fun onReload() {
        mainContentVM.getExchangeRate()
    }

    /** @see ErrorDialog.OnDialogButtonsClickListener.onExit */
    override fun onExit() {
        onBackPressed()
    }

    private fun onOpenCalculatorClick() {
        open_calculator_btn.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java)
                              .putParcelableArrayListExtra(NBU_CURRENCIES, nbuCurrency.currenciesList))
        }
    }

    private fun onChartSettingsClick() {
        chart_settings_button.setOnClickListener {
            if (isChartSettingsClosed) {
                openChartSettings()
                expandAppbar(delay = listAppearDuration / 2)
            }
            else {
                closeChartSettings(delay = listAppearDuration)
                collapseAppbar()
            }
        }
    }

    private fun onCurrenciesToDisplaySettingsClick() {
        currency_settings_button.setOnClickListener {
            if (isCurrenciesSettingsClosed) {
                openDisplayedCurrenciesSettings()
                expandAppbar(delay = listAppearDuration / 2)
            }
            else {
                closeDisplayedCurrenciesSettings(delay = listAppearDuration)
                collapseAppbar()
            }
        }
    }

    private fun onMainCheckboxClick() {
        main_checkbox_parent.setOnClickListener {
            main_checkbox.isChecked = !main_checkbox.isChecked
            displayCurrenciesAdapter.mainCheckboxClickEvent.onNext(main_checkbox.isChecked)
            StorageManager.saveVariable(MAIN_CHECKBOX_CHECKED, main_checkbox.isChecked)
        }
    }

    private fun onExchangeRateLoadFinish() {
        mainContentVM.exchangeRateObserver.observe(this, Observer {
                result: ResponseWrapper<Map<NbuCurrency, Boolean>> ->

            if (result.error == null && result.data != null) {
                displayCurrenciesAdapter.setItems(result.data)

                val currenciesToDisplay: MutableList<NbuCurrency> = mutableListOf()

                result.data.forEach {
                    val currencyShouldBeDisplayed: Boolean = it.value
                    if (currencyShouldBeDisplayed) {
                        currenciesToDisplay.add(it.key)
                    }
                }
                nbuExchangeAdapter.setItems(currenciesToDisplay)
                chartCurrenciesAdapter.setItems(currenciesToDisplay)
                nbuCurrency.currenciesList.clear()
                nbuCurrency.currenciesList.addAll(currenciesToDisplay)
            }
            else {
                errorDialog.show(supportFragmentManager, this.localClassName)
            }
        })
    }

    private fun onChartExchangeRateLoadFinish() {
        appbarViewModel.chartExchangeRate.observe(this, Observer {
                response: ResponseWrapper<MutableList<Double>> ->

                if (response.error == null) {
                    response.data?.let { exchangeRateList: MutableList<Double> ->
                        LineChartSettings(currenciesChart, exchangeRateList)
                    }
                }
            })
    }

    private fun onChartCurrencyChosen() {
        chartCurrenciesAdapter.chartCurrencyChosen.observe(this, Observer { currency: NbuCurrency ->
            currency.currencyAbbreviation?.let { abr: String ->
                appbarViewModel.getChartExchangeRate(abr)
            }
            closeChartSettings()
            collapseAppbar()
        })
    }

    private fun initBinding() {
        val binding: ActivityNbuBinding? = DataBindingUtil.bind(mainContentView)
        binding?.mainContentVM = mainContentVM
        binding?.appbarVM = appbarViewModel
    }

    private fun setupNbuExchangeList() {
        nbu_exchange_rate_list.setHasFixedSize(true)
        nbu_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        nbu_exchange_rate_list.adapter = nbuExchangeAdapter
    }

    private fun setupChartCurrenciesList() {
        chart_currency_choice_list.setHasFixedSize(true)
        chart_currency_choice_list.layoutManager = LinearLayoutManager(this)
        chart_currency_choice_list.addOnItemTouchListener(RecyclerViewTouchItemListener.listener)
        chart_currency_choice_list.adapter = chartCurrenciesAdapter
    }

    private fun setupDisplayedCurrenciesList() {
        displayed_currencies_list.setHasFixedSize(true)
        displayed_currencies_list.layoutManager = LinearLayoutManager(this)
        displayed_currencies_list.addOnItemTouchListener(RecyclerViewTouchItemListener.listener)
        displayed_currencies_list.adapter = displayCurrenciesAdapter
    }

    private fun expandAppbar(delay: Long = 0) {
        layoutParamsAnimation.apply {
            duration = resources.getInteger(R.integer.durationX4).toLong()
            view = app_bar
            targetHeight = resources.getDimension(R.dimen.app_bar_expanded_height).toInt()
        }
        Handler().postDelayed({ app_bar.startAnimation(layoutParamsAnimation) }, delay)
    }

    private fun collapseAppbar(delay: Long = 0) {
        layoutParamsAnimation.apply {
            duration = resources.getInteger(R.integer.durationX4).toLong()
            view = app_bar
            targetHeight = resources.getDimension(R.dimen.app_bar_height).toInt()
            app_bar.startAnimation(this)
        }
        Handler().postDelayed({ app_bar.startAnimation(layoutParamsAnimation) }, delay)
    }

    private fun openChartSettings() {
        if (!isCurrenciesSettingsClosed) {
            closeDisplayedCurrenciesSettings()
            setTransitionToEnd(CHART_SETTINGS_TRANSITION, delay = listAppearDuration)
        } else {
            setTransitionToEnd(CHART_SETTINGS_TRANSITION, delay = 0)
        }
        isChartSettingsClosed = false
    }

    private fun closeChartSettings(delay: Long = 0) {
        Handler().postDelayed({ app_bar_root_motion_layout.transitionToStart() }, delay)
        isChartSettingsClosed = true
    }

    private fun openDisplayedCurrenciesSettings() {
        if (!isChartSettingsClosed) {
            closeChartSettings()
            setTransitionToEnd(CURRENCIES_SETTINGS_TRANSITION, delay = listAppearDuration)
        } else {
            setTransitionToEnd(CURRENCIES_SETTINGS_TRANSITION, delay = 0)
        }
        isCurrenciesSettingsClosed = false
    }

    private fun closeDisplayedCurrenciesSettings(delay: Long = 0) {
        Handler().postDelayed({ app_bar_root_motion_layout.transitionToStart() }, delay)
        isCurrenciesSettingsClosed = true
    }

    private fun setTransitionToEnd(transitionType: Int, delay: Long = 0) {
        when (transitionType) {
            CHART_SETTINGS_TRANSITION -> {
                Handler().postDelayed({
                    app_bar_root_motion_layout.setTransition(
                        R.id.start_chart_settings_transition,
                        R.id.end_char_settings_transition
                    )
                    app_bar_root_motion_layout.transitionToEnd()
                }, delay)
            }
            CURRENCIES_SETTINGS_TRANSITION -> {
                Handler().postDelayed({
                    app_bar_root_motion_layout.setTransition(
                        R.id.start_currency_settings_transition,
                        R.id.end_currency_settings_transition
                    )
                    app_bar_root_motion_layout.transitionToEnd()
                }, delay)
            }
            else -> throw IllegalArgumentException("unknown type")
        }
    }

    companion object {
        private const val CHART_SETTINGS_TRANSITION = 0
        private const val CURRENCIES_SETTINGS_TRANSITION = 1
    }
}
