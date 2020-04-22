package com.jay.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.Banks
import com.jay.currencyconverter.model.currencyExchange.Organization
import com.jay.currencyconverter.service.CurrencyExchangeRateApi
import com.jay.currencyconverter.ui.adapter.BankExchangeAdapter
import com.jay.currencyconverter.viewmodel.BanksExchange
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_banks.*

class BanksActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    private lateinit var banksExchangeAdapter: BankExchangeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banks)

        setupRecyclerView()
        getBankExchangeRate()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        disposable.clear()
    }

    private fun setupRecyclerView() {
        banksExchangeAdapter = BankExchangeAdapter(context = this)
        banks_exchange_rate_list.setHasFixedSize(true)
        banks_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        banks_exchange_rate_list.adapter = banksExchangeAdapter
    }

    private fun getBankExchangeRate() {
        disposable.add(
            CurrencyExchangeRateApi.create(CurrencyExchangeRateApi.BANKS_EXCHANGE_URL)
                .getBanksExchangeRate()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result: Banks ->
                    banksExchangeAdapter.setItems(result.organizations)
                }, { error: Throwable ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                })
        )
    }
}