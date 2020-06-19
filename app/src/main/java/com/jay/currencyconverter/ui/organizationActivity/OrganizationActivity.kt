package com.jay.currencyconverter.ui.organizationActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding.widget.RxTextView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.databinding.ActivityOrganizationBinding
import com.jay.currencyconverter.di.organizationActivity.DaggerOrganizationActivityComponent
import com.jay.currencyconverter.model.exchangeRate.organization.CommonOrganization
import com.jay.currencyconverter.ui.NavigationActivity
import com.jay.currencyconverter.ui.adapter.OrganizationExchangeRateAdapter
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivity
import com.jay.currencyconverter.ui.dialog.ErrorDialog
import com.jay.currencyconverter.util.common.Constant.CURRENCIES
import com.jay.currencyconverter.util.common.Constant.OPEN_CALCULATOR_HINT_NOT_SHOWN
import com.jay.currencyconverter.util.common.Constant.ORGANIZATION
import com.jay.currencyconverter.util.common.Constant.SCROLL_BOTTOM
import com.jay.currencyconverter.util.common.StorageManager
import com.jay.currencyconverter.util.ui.SmoothScroller
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_organization.*
import kotlinx.android.synthetic.main.default_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OrganizationActivity : NavigationActivity(), ErrorDialog.OnDialogButtonsClickListener{

    @Inject
    lateinit var organizationExchangeRateAdapter: OrganizationExchangeRateAdapter

    @Inject
    lateinit var organizationActivityViewModel: OrganizationActivityViewModel

    private val linearLayoutManager = LinearLayoutManager(this)
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerOrganizationActivityComponent.builder().activity(this).build().inject(this)
        super.onCreate(savedInstanceState)
        initContent(R.layout.activity_organization, R.layout.default_toolbar)

        errorDialog.setOnDialogButtonsClickListener(this)

        initBinding()
        setupBanksExchangeRateList()
        observeExchangeRate()
        onOrganizationListItemClick()
        onOrganizationListScroll()
        onOrganizationSearch()
        onScrollToBottomBtnClick()
        onScrollToTopBtnClick()

        lifecycle.addObserver(organizationActivityViewModel)
        lifecycle.addObserver(organizationExchangeRateAdapter)

        app_bar_title.text = resources.getString(R.string.organization_exchange)
    }

    override fun onResume() {
        checkInternetConnection { organizationActivityViewModel.getExchangeRate() }
        super.onResume()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    /**@see ErrorDialog.OnDialogButtonsClickListener.onReload*/
    override fun onReload() {
        organizationActivityViewModel.getExchangeRate()
    }

    /**@see ErrorDialog.OnDialogButtonsClickListener.onExit*/
    override fun onExit() {
        onBackPressed()
    }

    private fun initBinding() {
        val binding: ActivityOrganizationBinding? = mainContentView?.let { DataBindingUtil.bind(it) }
        binding?.organizationVM = organizationActivityViewModel
    }

    private fun setupBanksExchangeRateList() {
        banks_exchange_rate_list.setHasFixedSize(true)
        banks_exchange_rate_list.layoutManager = linearLayoutManager
        banks_exchange_rate_list.adapter = organizationExchangeRateAdapter
    }

    private fun onOrganizationSearch() {
        RxTextView.textChanges(search_organizations_edit_text)
            .skip(1)
            .debounce(200, TimeUnit.MILLISECONDS)
            .map { it.toString().replace(oldValue = " ", newValue = "") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text: String ->
                organizationExchangeRateAdapter.filter(text)
            }
    }

    private fun observeExchangeRate() {
        organizationActivityViewModel.exchangeObserver.observe(this, Observer { response ->

            if (response.error == null) {
                response.data?.let {
                    organizationExchangeRateAdapter.setItems(it)
                    showOpenCalculatorHint()
                }
            } else {
                errorDialog.show(supportFragmentManager, this.localClassName)
            }
        })
    }

    private fun onOrganizationListItemClick() {
        organizationExchangeRateAdapter.clickEvent.observe(this, Observer { organization: CommonOrganization ->

            startActivity(Intent(this, CalculatorActivity::class.java)
                .putExtra(ORGANIZATION, organization)
                .putExtra(CURRENCIES, organization.currencies))
        })
    }

    private fun onOrganizationListScroll() {
        banks_exchange_rate_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0) {
                    scroll_to_top_btn.show()
                    scroll_to_bottom_btn.hide()
                } else if (dy > 0){
                    scroll_to_top_btn.hide()
                    scroll_to_bottom_btn.show()
                }

                if (!recyclerView.canScrollVertically(SCROLL_BOTTOM)){
                    scroll_to_top_btn.show()
                    scroll_to_bottom_btn.hide()
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun onScrollToTopBtnClick() {
        scroll_to_top_btn.setOnClickListener {
            SmoothScroller.getSmoothScroller().targetPosition = 0
            linearLayoutManager.startSmoothScroll(SmoothScroller.getSmoothScroller())
        }
    }

    private fun onScrollToBottomBtnClick() {
        scroll_to_bottom_btn.setOnClickListener {
            SmoothScroller.getSmoothScroller().targetPosition =
                organizationExchangeRateAdapter.itemCount
            linearLayoutManager.startSmoothScroll(SmoothScroller.getSmoothScroller())
        }
    }

    private fun showOpenCalculatorHint() {
        if (StorageManager.getVariable(OPEN_CALCULATOR_HINT_NOT_SHOWN, true)){

            val subscribe = Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe {
                    val view: View? =
                        banks_exchange_rate_list.layoutManager?.findViewByPosition(0)

                    view?.let {
                        GuideView.Builder(this@OrganizationActivity)
                            .setTitle("Tap on the organization to open the currency calculator")
                            .setTargetView(it)
                            .setDismissType(DismissType.outside)
                            .build()
                            .show()
                        StorageManager.saveVariable(OPEN_CALCULATOR_HINT_NOT_SHOWN, false)
                    }
                }
            disposable.add(subscribe)
        }
    }
}