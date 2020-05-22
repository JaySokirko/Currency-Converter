package com.jay.currencyconverter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityNbuBinding
import com.jay.currencyconverter.di.DaggerNbuActivityComponent
import com.jay.currencyconverter.ui.adapter.NbuExchangeAdapter
import com.jay.currencyconverter.viewModel.NbuActivityMV
import kotlinx.android.synthetic.main.activity_nbu.*
import javax.inject.Inject

class NbuActivity : AppCompatActivity() {

    @Inject
    lateinit var nbuExchangeAdapter: NbuExchangeAdapter

    @Inject
    lateinit var nbuActivityVM: NbuActivityMV

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerNbuActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)

        initBinding()
        setupNbuExchangeList()

        nbuActivityVM.getExchangeRate()

        observeExchangeRate()
    }

    override fun onDestroy() {
        nbuActivityVM.onDestroy()
        super.onDestroy()
    }

    private fun observeExchangeRate() {
        nbuActivityVM.exchangeObserver.observe(this, Observer { response ->

            if (response.error == null) {
                nbuExchangeAdapter.setItems(response.data!!)
            } else {
                //todo handle error
            }
        })
    }

    private fun initBinding() {
        val binding: ActivityNbuBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_nbu)
        binding.nbuVM = nbuActivityVM
    }

    private fun setupNbuExchangeList() {
        nbu_exchange_rate_list.setHasFixedSize(true)
        nbu_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        nbu_exchange_rate_list.adapter = nbuExchangeAdapter
    }
}
