package com.jay.currencyconverter.ui.bankActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.Organization
import com.jay.currencyconverter.ui.CalculatorActivity
import com.jay.currencyconverter.ui.adapter.BankExchangeAdapter
import io.reactivex.disposables.Disposable
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

        onRecyclerItemClickHandler()
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

    private fun onRecyclerItemClickHandler() {
        banksExchangeAdapter.clickEvent.observe(this, Observer { organizations ->
            startActivity(Intent(this, CalculatorActivity::class.java)
                    .putExtra("organization", organizations)
                    .putExtra("currencies", organizations.currencies))
        })
    }
}