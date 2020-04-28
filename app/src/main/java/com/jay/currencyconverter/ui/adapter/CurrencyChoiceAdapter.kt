package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.model.currencyExchange.currency.CurrencyType
import io.reactivex.subjects.BehaviorSubject

class CurrencyChoiceAdapter() : RecyclerView.Adapter<BaseViewHolder<Currency>>() {

    private val currencyList: MutableList<Currency> = ArrayList()
    private val viewHolderList: MutableList<BaseViewHolder<Currency>> = mutableListOf()
    val clickEvent: BehaviorSubject<Helper> = BehaviorSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Currency> {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_currency_choice, parent, false)

        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Currency>, position: Int) {
        viewHolderList.add(holder)
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setItems(currencies: List<Currency?>) {
        currencyList.clear()
        currencyList.addAll(currencies.filterNotNull())
        notifyDataSetChanged()
    }

    inner class CurrencyVH(itemView: View) : BaseViewHolder<Currency>(itemView) {

        private val baseCurrencyBtn: MaterialButton = itemView.findViewById(R.id.base_currency_btn)
        private val conversionCurrencyBtn: MaterialButton =
            itemView.findViewById(R.id.conversion_currency_btn)

        init {
            baseCurrencyBtn.setOnClickListener { onBaseCurrencyBtnClick() }
            conversionCurrencyBtn.setOnClickListener { onConversionCurrencyBtnClick() }
        }

        override fun bind(item: Currency) {
            baseCurrencyBtn.text = item.getAbr(itemView.context)
            baseCurrencyBtn.icon = item.getImage(itemView.context)
            conversionCurrencyBtn.text = item.getAbr(itemView.context)
            conversionCurrencyBtn.icon = item.getImage(itemView.context)
        }

        private fun onBaseCurrencyBtnClick() {
            clickEvent.onNext(
                Helper(
                    selectedPosition = layoutPosition,
                    selectedCurrency = currencyList[layoutPosition],
                    currencyType = CurrencyType.BASE
                )
            )
        }

        private fun onConversionCurrencyBtnClick() {
            clickEvent.onNext(
                Helper(
                    selectedPosition = layoutPosition,
                    selectedCurrency = currencyList[layoutPosition],
                    currencyType = CurrencyType.CONVERSION
                )
            )
        }
    }

    class Helper(
        var selectedPosition: Int? = null, var selectedCurrency: Currency? = null,
        var state: AdapterItemVisibility? = null, val currencyType: CurrencyType? = null
    ) {
        enum class AdapterItemVisibility { INVISIBLE, VISIBLE }
    }
}