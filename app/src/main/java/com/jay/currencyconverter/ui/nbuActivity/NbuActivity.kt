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
import com.jay.currencyconverter.ui.LineChartSetup
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.util.Constant.EMPTY_STRING
import kotlinx.android.synthetic.main.activity_nbu.*
import javax.inject.Inject

class NbuActivity : NavigationActivity(), ErrorDialog.ErrorDialogClickListener {

    @Inject
    lateinit var nbuExchangeAdapter: NbuExchangeAdapter

    @Inject
    lateinit var nbuActivityVM: NbuActivityViewModel

    private val errorDialog: ErrorDialog = ErrorDialog()
    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNbuActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_nbu, R.layout.default_toolbar)

        initBinding()

        lineChart = findViewById(R.id.line_chart)
        lineChart.setNoDataText(EMPTY_STRING)

        errorDialog.setOnErrorDialogClickListener(this)

        setupNbuExchangeList()

        nbuActivityVM.getExchangeRate()
        nbuActivityVM.getPreviousExchangeRate()

        observeExchangeRate()
        observePreviousExchangeRate()
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

    private fun observeExchangeRate() {
        nbuActivityVM.exchangeRateObserver.observe(this, Observer {
                response: ResponseWrapper<List<Nbu>> ->

            if (response.error == null) {
                nbuExchangeAdapter.setItems(response.data!!)
            } else {
                errorDialog.show(supportFragmentManager, this.localClassName)
            }
        })
    }

    private fun observePreviousExchangeRate() {
        nbuActivityVM.previousExchangeRateObserver.observe(this, Observer {
                response: ResponseWrapper<MutableList<Double>> ->

            if (response.error == null){
                LineChartSetup(lineChart, response.data!!)
            }
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
}
