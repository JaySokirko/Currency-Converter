package com.jay.currencyconverter.ui.organizationActivity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityOrganizationBinding
import com.jay.currencyconverter.di.DaggerBankActivityComponent
import com.jay.currencyconverter.model.exchangeRate.organization.Organization
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivity
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.ui.adapter.OrganizationExchangeRateAdapter
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.util.common.Constant.CURRENCIES
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION
import kotlinx.android.synthetic.main.activity_organization.*
import javax.inject.Inject

class OrganizationActivity : NavigationActivity(), ErrorDialog.OnDialogButtonsClickListener {

    @Inject
    lateinit var organizationExchangeRateAdapter: OrganizationExchangeRateAdapter

    @Inject
    lateinit var organizationActivityViewModel: OrganizationActivityViewModel

    private val errorDialog = ErrorDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBankActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_organization, R.layout.default_toolbar)

        errorDialog.setOnDialogButtonsClickListener(this)

        initBinding()
        setupBanksExchangeRateList()

        organizationActivityViewModel.getExchangeRate()

        observeExchangeRate()
        onBankExchangeRateListItemClick()

        lifecycle.addObserver(organizationActivityViewModel)
    }

    /**
     * @see ErrorDialog.OnDialogButtonsClickListener.onReload
     */
    override fun onReload() {
        organizationActivityViewModel.getExchangeRate()
    }

    /**
     * @see ErrorDialog.OnDialogButtonsClickListener.onExit
     */
    override fun onExit() {
        onBackPressed()
    }

    private fun observeExchangeRate() {
        organizationActivityViewModel.exchangeObserver.observe(this, Observer { response ->

            if (response.error == null) {
                organizationExchangeRateAdapter.setItems(response.data!!)
            } else {
                errorDialog.show(supportFragmentManager, this.localClassName)
            }
        })
    }

    private fun initBinding() {
        val binding: ActivityOrganizationBinding? = DataBindingUtil.bind(mainContentView)
        binding?.bankVM = organizationActivityViewModel
    }

    private fun setupBanksExchangeRateList() {
        banks_exchange_rate_list.setHasFixedSize(true)
        banks_exchange_rate_list.layoutManager = LinearLayoutManager(this)
        banks_exchange_rate_list.adapter = organizationExchangeRateAdapter
    }

    private fun onBankExchangeRateListItemClick() {
        organizationExchangeRateAdapter.clickEvent.observe(this, Observer { organization: Organization ->

            startActivity(Intent(this, CalculatorActivity::class.java)
                .putExtra(ORGANIZATION, organization)
                .putExtra(CURRENCIES, organization.currencies))
        })
    }
}