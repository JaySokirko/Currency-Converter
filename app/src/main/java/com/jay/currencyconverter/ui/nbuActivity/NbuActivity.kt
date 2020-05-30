package com.jay.currencyconverter.ui.nbuActivity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityNbuBinding
import com.jay.currencyconverter.di.DaggerNbuActivityComponent
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.util.LineChartSetup
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import kotlinx.android.synthetic.main.activity_nbu.*
import javax.inject.Inject

class NbuActivity : NavigationActivity(), ErrorDialog.ErrorDialogClickListener {

    @Inject
    lateinit var nbuExchangeAdapter: NbuExchangeAdapter

    @Inject
    lateinit var nbuActivityVM: NbuActivityViewModel

    private val errorDialog: ErrorDialog = ErrorDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNbuActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_nbu, R.layout.default_toolbar)

        initBinding()

        errorDialog.setOnErrorDialogClickListener(this)

        setupNbuExchangeList()

        nbuActivityVM.getExchangeRate()
        nbuActivityVM.getPreviousExchangeRate()

        observeExchangeRate()

        LineChartSetup(findViewById(R.id.line_chart))
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
        nbuActivityVM.exchangeObserver.observe(this, Observer { response ->

            if (response.error == null) {
                nbuExchangeAdapter.setItems(response.response!!)
            } else {
                errorDialog.show(supportFragmentManager, this.localClassName)
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
