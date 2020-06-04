package com.jay.currencyconverter.ui.nbuActivity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityNbuBinding
import com.jay.currencyconverter.di.DaggerNbuActivityComponent
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.ui.adapter.DisplayedCurrenciesAdapter
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.util.Constant.CURRENCY_ABR
import com.jay.currencyconverter.util.Constant.CURRENCY_ABR_DEFAULT
import com.jay.currencyconverter.util.Constant.EMPTY_STRING
import com.jay.currencyconverter.util.Constant.MAIN_CHECKBOX_CHECKED
import com.jay.currencyconverter.util.StorageManager
import com.jay.currencyconverter.util.ui.LayoutParamsAnimator
import com.jay.currencyconverter.util.ui.LineChartSettings
import com.jay.currencyconverter.util.ui.RecyclerViewTouchItemListener
import kotlinx.android.synthetic.main.activity_nbu.*
import kotlinx.android.synthetic.main.layout_currency_settings.*
import javax.inject.Inject


class NbuActivity : NavigationActivity(), ErrorDialog.ErrorDialogClickListener {

    @Inject
    lateinit var nbuExchangeAdapter: NbuExchangeAdapter

    @Inject
    lateinit var chartCurrenciesAdapter: CurrencyChoiceAdapter

    @Inject
    lateinit var displayCurrenciesAdapter: DisplayedCurrenciesAdapter

    @Inject
    lateinit var nbuActivityVM: NbuActivityViewModel

    val nbuCurrenciesDisplay = NbuCurrenciesDisplay()
    val errorDialog: ErrorDialog = ErrorDialog()
    val layoutParamsAnimator = LayoutParamsAnimator()

    private lateinit var currenciesChart: LineChart
    private var isChartSettingsClosed = true
    private var isCurrenciesSettingsClosed = true
    private val CHART_SETTINGS_TRANSITION = 0
    private val CURRENCIES_SETTINGS_TRANSITION = 1
    private var transitionDuration: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNbuActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_nbu, R.layout.default_toolbar)

        initBinding()

        setupNbuExchangeList()
        setupChartCurrenciesList()
        setupDisplayedCurrenciesList()

        onExchangeRateLoadFinish()
        onPreviousExchangeRateLoadFinish()

        onDisplayedCurrenciesListChanged()

        onChartSettingsClick()
        onChartCurrencyChosen()

        onDisplayedCurrenciesSettingsClick()
        onMainCheckboxClick()

        currenciesChart = findViewById(R.id.line_chart)
        currenciesChart.setNoDataText(EMPTY_STRING)

        errorDialog.setOnErrorDialogClickListener(this)

        nbuActivityVM.getExchangeRate()
        nbuActivityVM.getPreviousExchangeRate(StorageManager.getVariable(CURRENCY_ABR, CURRENCY_ABR_DEFAULT))

        transitionDuration =
            resources.getInteger(R.integer.activity_nbu_scene_transition_duration).toLong()

        lifecycle.addObserver(displayCurrenciesAdapter)
        lifecycle.addObserver(nbuActivityVM)
        lifecycle.addObserver(nbuCurrenciesDisplay)
    }

    /**
     * @see ErrorDialog.ErrorDialogClickListener.onReload
     */
    override fun onReload() {
        nbuActivityVM.getExchangeRate()
    }

    /**
     * @see ErrorDialog.ErrorDialogClickListener.onExit
     */
    override fun onExit() {
        onBackPressed()
    }

    private fun onChartSettingsClick() {
        chart_settings_button.setOnClickListener {
            if (isChartSettingsClosed) {
                openChartSettings()
                expandAppbar()
            } else {
                closeChartSettings()
                collapseAppbar()
            }
        }
    }

    private fun onDisplayedCurrenciesSettingsClick() {
        currency_settings_button.setOnClickListener {
            if (isCurrenciesSettingsClosed) {
                openDisplayedCurrenciesSettings()
                expandAppbar()
            } else {
                closeDisplayedCurrenciesSettings()
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
        nbuActivityVM.exchangeRateObserver.observe(this, Observer {
                response: ResponseWrapper<List<Nbu>> ->

                if (response.error == null) {
                    response.data?.let { nbuCurrenciesList: List<Nbu> ->
                        nbuCurrenciesDisplay.setItems(nbuCurrenciesList)
                    }
                } else {
                    errorDialog.show(supportFragmentManager, this.localClassName)
                }
            })
    }

    private fun onPreviousExchangeRateLoadFinish() {
        nbuActivityVM.previousExchangeRateObserver.observe(this, Observer {
                response: ResponseWrapper<MutableList<Double>> ->

                if (response.error == null) {
                    response.data?.let { exchangeRateList: MutableList<Double> ->
                        LineChartSettings(currenciesChart, exchangeRateList)
                    }
                }
            })
    }

    private fun onDisplayedCurrenciesListChanged() {
        nbuCurrenciesDisplay.displayedCurrenciesLiveData.observe(this,
            Observer { result: MutableMap<Nbu, Boolean> ->
                displayCurrenciesAdapter.setItems(result)

                val currenciesToDisplay: MutableList<Nbu> = mutableListOf()

                result.forEach{
                    val currencyShouldBeDisplayed: Boolean = it.value
                    if (currencyShouldBeDisplayed){
                        currenciesToDisplay.add(it.key)
                    }
                }
                nbuExchangeAdapter.setItems(currenciesToDisplay)
                chartCurrenciesAdapter.setItems(currenciesToDisplay)
            })
    }

    private fun onChartCurrencyChosen() {
        chartCurrenciesAdapter.clickEvent.observe(this, Observer { currency: Nbu ->
            currency.currencyAbbreviation?.let { abr: String ->
                nbuActivityVM.getPreviousExchangeRate(abr)
            }
            closeChartSettings()
            collapseAppbar()
        })
    }

    private fun initBinding() {
        val binding: ActivityNbuBinding? = DataBindingUtil.bind(mainContentView)
        binding?.nbuVM = nbuActivityVM
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

    private fun expandAppbar() {
        layoutParamsAnimator.apply {
            duration = resources.getInteger(R.integer.activity_nbu_scene_transition_duration).toLong()
            view = app_bar
            targetHeight = resources.getDimension(R.dimen.app_bar_expanded_height).toInt()
            app_bar.startAnimation(this)
        }
    }

    private fun collapseAppbar() {
        layoutParamsAnimator.apply {
            duration = resources.getInteger(R.integer.activity_nbu_scene_transition_duration).toLong()
            view = app_bar
            targetHeight = resources.getDimension(R.dimen.app_bar_height).toInt()
            app_bar.startAnimation(this)
        }
    }

    private fun openChartSettings() {
        if (!isCurrenciesSettingsClosed) {
            closeDisplayedCurrenciesSettings()
            setTransitionToEnd(CHART_SETTINGS_TRANSITION, transitionDuration)
        } else {
            setTransitionToEnd(CHART_SETTINGS_TRANSITION, duration = 0)
        }
        isChartSettingsClosed = false
    }

    private fun closeChartSettings() {
        app_bar_root_motion_layout.transitionToStart()
        isChartSettingsClosed = true
    }

    private fun openDisplayedCurrenciesSettings() {
        if (!isChartSettingsClosed) {
            closeChartSettings()
            setTransitionToEnd(CURRENCIES_SETTINGS_TRANSITION, transitionDuration)
        } else {
            setTransitionToEnd(CURRENCIES_SETTINGS_TRANSITION, duration = 0)
        }
        isCurrenciesSettingsClosed = false
    }

    private fun closeDisplayedCurrenciesSettings() {
        app_bar_root_motion_layout.transitionToStart()
        isCurrenciesSettingsClosed = true
    }

    private fun setTransitionToEnd(transitionType: Int, duration: Long) {
        when (transitionType) {
            CHART_SETTINGS_TRANSITION -> {
                Handler().postDelayed({
                    app_bar_root_motion_layout.setTransition(
                        R.id.start_chart_settings_transition,
                        R.id.end_char_settings_transition
                    )
                    app_bar_root_motion_layout.transitionToEnd()
                }, duration)
            }
            CURRENCIES_SETTINGS_TRANSITION -> {
                Handler().postDelayed({
                    app_bar_root_motion_layout.setTransition(
                        R.id.start_currency_settings_transition,
                        R.id.end_currency_settings_transition
                    )
                    app_bar_root_motion_layout.transitionToEnd()
                }, duration)
            }
            else -> throw IllegalArgumentException("unknown type")
        }
    }
}
