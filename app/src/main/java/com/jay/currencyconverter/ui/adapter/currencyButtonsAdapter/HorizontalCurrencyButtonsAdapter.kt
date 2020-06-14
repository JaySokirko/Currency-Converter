package com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding.view.RxView
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R
import com.jay.currencyconverter.customView.CustomMaterialButton
import com.jay.currencyconverter.model.CurrencyChoice
import com.jay.currencyconverter.model.exchangeRate.Currency
import com.jay.currencyconverter.model.exchangeRate.CurrencyType
import com.jay.currencyconverter.ui.adapter.CurrencyDiffUtil
import com.jay.currencyconverter.ui.adapter.viewHolder.BaseViewHolder
import com.jay.currencyconverter.util.common.Filter
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class HorizontalCurrencyButtonsAdapter : RecyclerView.Adapter<BaseViewHolder<Currency>>(),
    LifecycleObserver {

    val currencyButtonClick: MutableLiveData<CurrencyChoice> = MutableLiveData()

    private val currencyList: MutableList<Currency> = ArrayList()
    private val buttonsBehaviour = CurrencyButtonsBehaviour()
    private val filter: Filter<Currency> = Filter()
    private val diffUtil = CurrencyDiffUtil()
    private val context: Context = BaseApplication.baseComponent.application.baseContext
    private var reduceTextDuration = 0L

    init {
        reduceTextDuration = context.resources.getInteger(R.integer.text_reduce_duration).toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Currency> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_horizontal_currency_buttons, parent, false)

        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Currency>, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: List<Currency?>) {
        diffUtil.setData(oldList = currencyList, newList = currencies.filterNotNull())
        currencyList.apply { clear(); addAll(currencies.filterNotNull()) }
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
    }

    fun getItemPositionBySearch(search: String): Int{
        if (search.isBlank()) return 0

        val filteredResult: MutableList<Currency> =
            filter.getFilteredResult(search, currencyList) { currency: Currency ->
                currency.getName(context)?.toLowerCase()?.contains(search.toLowerCase()) ?: false ||
                currency.getAbr(context)?.toLowerCase()?.contains(search.toLowerCase()) ?: false
            }

        currencyList.forEachIndexed  { index, currency ->
            val find: Currency? = filteredResult.find { it.getAbr(context) == currency.getAbr(context) }
            if (find != null) return index
        }

        return 0
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        buttonsBehaviour.onDestroy()
    }

    private inner class CurrencyVH(itemView: View) : BaseViewHolder<Currency>(itemView) {

        private val baseCurrencyBtn: CustomMaterialButton =
            itemView.findViewById(R.id.base_currency_btn)

        private val conversionCurrencyBtn: CustomMaterialButton =
            itemView.findViewById(R.id.conversion_currency_btn)

        init {
            buttonsBehaviour.baseButtons.add(baseCurrencyBtn)
            buttonsBehaviour.conversionButtons.add(conversionCurrencyBtn)
            buttonsBehaviour.setup()

            RxView.clicks(baseCurrencyBtn)
                .throttleFirst(reduceTextDuration, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    buttonsBehaviour.onBaseButtonClick(baseCurrencyBtn)
                    currencyButtonClick.postValue(CurrencyChoice(currencyList[layoutPosition],
                                                                 CurrencyType.BASE,
                                                                 baseCurrencyBtn.isPressedState))
                }

             RxView.clicks(conversionCurrencyBtn)
                .throttleFirst(reduceTextDuration, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    buttonsBehaviour.onConversionButtonClick(conversionCurrencyBtn)
                    currencyButtonClick.postValue(CurrencyChoice(currencyList[layoutPosition],
                                                                 CurrencyType.CONVERSION,
                                                                 conversionCurrencyBtn.isPressedState))
                }
        }

        override fun bind(item: Currency) {
            baseCurrencyBtn.text = item.getAbr(itemView.context)
            baseCurrencyBtn.icon = item.getImage(itemView.context)

            conversionCurrencyBtn.text = item.getAbr(itemView.context)
            conversionCurrencyBtn.icon = item.getImage(itemView.context)
        }
    }
}