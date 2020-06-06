package com.jay.currencyconverter.ui.bankActivity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityBanksBinding
import com.jay.currencyconverter.di.DaggerBankActivityComponent
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivity
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.ui.adapter.BankExchangeRateAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.util.Constant
import kotlinx.android.synthetic.main.activity_banks.*
import javax.inject.Inject

class BankActivity : NavigationActivity(), ErrorDialog.OnDialogButtonsClickListener {

    @Inject
    lateinit var bankExchangeRateAdapter: BankExchangeRateAdapter

    @Inject
    lateinit var bankActivityViewModel: BankActivityViewModel

    private val errorDialog = ErrorDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBankActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_banks, R.layout.default_toolbar)

        errorDialog.setOnDialogButtonsClickListener(this)

        initBinding()
        setupBanksExchangeRateList()

        bankActivityViewModel.getExchangeRate()

        observeExchangeRate()
        onBankExchangeRateListItemClick()

        lifecycle.addObserver(bankActivityViewModel)
    }

    /**
     * @see ErrorDialog.OnDialogButtonsClickListener.onReload
     */
    override fun onReload() {
        bankActivityViewModel.getExchangeRate()
    }

    /**
     * @see ErrorDialog.OnDialogButtonsClickListener.onExit
     */
    override fun onExit() {
        onBackPressed()
    }

    private fun observeExchangeRate() {
        bankActivityViewModel.exchangeObserver.observe(this, Observer { response ->

            if (response.error == null) {
                bankExchangeRateAdapter.setItems(response.data!!)
            } else {
                errorDialog.show(supportFragmentManager, this.localClassName)
            }
        })
    }

    private fun initBinding() {
        val binding: ActivityBanksBinding? = DataBindingUtil.bind(mainContentView)
        binding?.bankVM = bankActivityViewModel
    }

    private fun setupBanksExchangeRateList() {
        banks_exchange_rate_list.setHasFixedSize(true)
        banks_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        banks_exchange_rate_list.adapter = bankExchangeRateAdapter
    }

    private fun onBankExchangeRateListItemClick() {
        bankExchangeRateAdapter.clickEvent.observe(this, Observer { organization ->

            startActivity(
                Intent(this, CalculatorActivity::class.java)
                    .putExtra(Constant.ORGANIZATION_TITLE, organization.title)
                    .putExtra(Constant.CURRENCIES, organization.currencies)
            )
        })
    }
}