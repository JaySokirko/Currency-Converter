package com.jay.currencyconverter.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityBanksBinding
import com.jay.currencyconverter.di.DaggerBankActivityComponent
import com.jay.currencyconverter.ui.adapter.BankExchangeRateAdapter
import com.jay.currencyconverter.util.Constant
import com.jay.currencyconverter.viewModel.BankActivityVM
import kotlinx.android.synthetic.main.activity_banks.*
import javax.inject.Inject

class BankActivity : AppCompatActivity() {

    @Inject
    lateinit var bankExchangeRateAdapter: BankExchangeRateAdapter

    @Inject
    lateinit var bankActivityVM: BankActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBankActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)

        initBinding()
        setupBanksExchangeRateList()

        bankActivityVM.getExchangeRate()

        observeExchangeRate()
        onBankExchangeRateListItemClick()
    }

    override fun onDestroy() {
        bankActivityVM.onDestroy()
        super.onDestroy()
    }

    private fun observeExchangeRate() {
        bankActivityVM.exchangeObserver.observe(this, Observer { response ->

            if (response.error == null) {
                bankExchangeRateAdapter.setItems(response.data!!)
            } else {
                //todo handle error
            }
        })
    }

    private fun initBinding() {
        val binding: ActivityBanksBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_banks)
        binding.bankVM = bankActivityVM
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