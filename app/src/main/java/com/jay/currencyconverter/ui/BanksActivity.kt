package com.jay.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.Banks
import com.jay.currencyconverter.ui.adapter.BankExchangeAdapter
import com.jay.currencyconverter.viewmodel.BanksExchange
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_banks.*

class BanksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banks)

        val adapter = BankExchangeAdapter(context = this)
        banks_exchange_rate_list.setHasFixedSize(true)
        banks_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        banks_exchange_rate_list.adapter = adapter

        val banksExchange= BanksExchange()
        val subscribe = banksExchange.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { organizations ->
                adapter.setItems(organizations)
            }
    }
}