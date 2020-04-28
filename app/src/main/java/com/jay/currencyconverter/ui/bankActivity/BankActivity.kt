package com.jay.currencyconverter.ui.bankActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.Organization
import com.jay.currencyconverter.ui.CalculatorActivity
import com.jay.currencyconverter.ui.adapter.BankExchangeRateAdapter
import com.jay.currencyconverter.util.Constant
import kotlinx.android.synthetic.main.activity_banks.*

class BankActivity : AppCompatActivity(), IBankView {

    private lateinit var bankExchangeRateAdapter: BankExchangeRateAdapter
    private lateinit var bankPresenter: BankPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banks)

        setupBanksExchangeRateAdapter()

        bankPresenter = BankPresenter(view = this)
        bankPresenter.getExchangeRate()

        onBankExchangeRateAdapterItemClick()
    }

    override fun onDestroy() {
        bankPresenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    override fun onExchangeRateLoadFinish(list: List<Organization>) {
        bankExchangeRateAdapter.setItems(list)
    }

    override fun onExchangeRateLoadError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

    private fun setupBanksExchangeRateAdapter() {
        bankExchangeRateAdapter = BankExchangeRateAdapter(context = this)
        banks_exchange_rate_list.setHasFixedSize(true)
        banks_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        banks_exchange_rate_list.adapter = bankExchangeRateAdapter
    }

    private fun onBankExchangeRateAdapterItemClick() {
        bankExchangeRateAdapter.clickEvent.observe(this, Observer { organizations ->
            val organizationTitle: String = organizations.title!!
            startActivity(Intent(this, CalculatorActivity::class.java)
                    .putExtra(Constant.ORGANIZATION_NAME, organizationTitle)
                    .putExtra(Constant.CURRENCIES, organizations.currencies))
        })
    }
}