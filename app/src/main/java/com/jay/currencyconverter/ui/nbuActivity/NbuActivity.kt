package com.jay.currencyconverter.ui.nbuActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import kotlinx.android.synthetic.main.activity_nbu.*

class NbuActivity : AppCompatActivity(), INbuView {

    private val nbuExchangeAdapter: NbuExchangeAdapter = NbuExchangeAdapter()
    private lateinit var nbuPresenter: NbuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nbu)

        nbuPresenter = NbuPresenter(this, this)
        nbuPresenter.getExchangeRate()

        setupNbuExchangeList()
    }

    override fun onDestroy() {
        nbuPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onExchangeRateLoadFinish(list: List<Nbu>) {
        nbuExchangeAdapter.setItems(list)
    }

    override fun onExchangeRateLoadError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    private fun setupNbuExchangeList(){
        nbu_exchange_rate_list.setHasFixedSize(true)
        nbu_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        nbu_exchange_rate_list.adapter = nbuExchangeAdapter
    }
}