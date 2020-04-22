package com.jay.currencyconverter.ui.bankActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.Organization
import com.jay.currencyconverter.ui.adapter.BankExchangeAdapter
import kotlinx.android.synthetic.main.activity_banks.*

class BankActivity : AppCompatActivity(), IBankView {

    private lateinit var banksExchangeAdapter: BankExchangeAdapter
    private lateinit var banksPresenter: BankPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banks)

        setupRecyclerView()

        banksPresenter = BankPresenter(view = this)
        banksPresenter.getExchangeRate()
    }

    override fun onDestroy() {
        banksPresenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    override fun onExchangeRateLoadFinish(list: List<Organization>) {
        banksExchangeAdapter.setItems(list)
    }

    override fun onExchangeRateLoadError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView() {
        banksExchangeAdapter = BankExchangeAdapter(context = this)
        banks_exchange_rate_list.setHasFixedSize(true)
        banks_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        banks_exchange_rate_list.adapter = banksExchangeAdapter
    }
}