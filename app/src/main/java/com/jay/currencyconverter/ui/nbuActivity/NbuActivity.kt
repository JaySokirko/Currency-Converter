package com.jay.currencyconverter.ui.nbuActivity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityNbuBinding
import com.jay.currencyconverter.di.DaggerNbuActivityComponent
import com.jay.currencyconverter.model.ResponseWrapper
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.util.ui.LineChartSettings
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.util.ui.RecyclerViewTouchItemListener
import com.jay.currencyconverter.ui.adapter.CurrencyChoiceAdapter
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.util.Constant.CURRENCY_ABR
import com.jay.currencyconverter.util.Constant.CURRENCY_ABR_DEFAULT
import com.jay.currencyconverter.util.Constant.EMPTY_STRING
import com.jay.currencyconverter.util.StorageManager
import kotlinx.android.synthetic.main.activity_nbu.*
import javax.inject.Inject

class NbuActivity : NavigationActivity(), ErrorDialog.ErrorDialogClickListener {

    @Inject
    lateinit var nbuExchangeAdapter: NbuExchangeAdapter

    @Inject
    lateinit var currencyChoiceAdapter: CurrencyChoiceAdapter

    @Inject
    lateinit var nbuActivityVM: NbuActivityViewModel

    private val errorDialog: ErrorDialog = ErrorDialog()
    private lateinit var lineChart: LineChart
    private var appBarSettingsClosed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNbuActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_nbu, R.layout.default_toolbar)

        initBinding()
        setupNbuExchangeList()
        setupCurrencyChoiceList()
        observeExchangeRate()
        observePreviousExchangeRate()
        observeCurrencyChoice()
        onSettingsButtonClick()

        lineChart = findViewById(R.id.line_chart)
        lineChart.setNoDataText(EMPTY_STRING)

        errorDialog.setOnErrorDialogClickListener(this)

        nbuActivityVM.getExchangeRate()
        nbuActivityVM.getPreviousExchangeRate(StorageManager.getVariable(CURRENCY_ABR,
            CURRENCY_ABR_DEFAULT))
    }

    override fun onDestroy() {
        nbuActivityVM.onDestroy()
        super.onDestroy()
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

    private fun onSettingsButtonClick(){
        settings_button.setOnClickListener {
            if (appBarSettingsClosed){
                expandAppbarSettings()
            } else {
                collapseAppbarSettings()
            }
        }
    }

    private fun observeExchangeRate() {
        nbuActivityVM.exchangeRateObserver.observe(this, Observer {
                response: ResponseWrapper<List<Nbu>> ->

            if (response.error == null) {
                response.data?.let { nbuCurrenciesList: List<Nbu> ->
                    nbuExchangeAdapter.setItems(nbuCurrenciesList)
                    currencyChoiceAdapter.setItems(nbuCurrenciesList)
                }
            } else {
                errorDialog.show(supportFragmentManager, this.localClassName)
            }
        })
    }

    private fun observePreviousExchangeRate() {
        nbuActivityVM.previousExchangeRateObserver.observe(this, Observer {
                response: ResponseWrapper<MutableList<Double>> ->

            if (response.error == null) {
                response.data?.let { exchangeRateList: MutableList<Double> ->
                    LineChartSettings(lineChart, exchangeRateList)
                }
            }
        })
    }

    private fun observeCurrencyChoice(){
        currencyChoiceAdapter.clickEvent.observe(this, Observer { currency: Nbu ->
            currency.currencyAbbreviation?.let { abr: String ->
                nbuActivityVM.getPreviousExchangeRate(abr)
            }
            collapseAppbarSettings()
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

    private fun setupCurrencyChoiceList(){
        currency_choice_list.setHasFixedSize(true)
        currency_choice_list.layoutManager = LinearLayoutManager(this)
        currency_choice_list.addOnItemTouchListener(RecyclerViewTouchItemListener.listener)
        currency_choice_list.adapter = currencyChoiceAdapter
    }

    private fun expandAppbarSettings(){
        app_bar_root_motion_layout.transitionToEnd()
        appBarSettingsClosed = false
    }

    private fun collapseAppbarSettings(){
        app_bar_root_motion_layout.transitionToStart()
        appBarSettingsClosed = true
    }
}
