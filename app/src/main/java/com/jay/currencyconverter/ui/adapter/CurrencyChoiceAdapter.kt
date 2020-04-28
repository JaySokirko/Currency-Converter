package com.jay.currencyconverter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.model.currencyExchange.currency.CurrencyType
import io.reactivex.subjects.BehaviorSubject

class CurrencyChoiceAdapter(private val context: Context) :
    RecyclerView.Adapter<BaseViewHolder<Currency>>() {

    private val baseCurrencyList: MutableList<Currency> = ArrayList()
    private val conversionCurrencyList: MutableList<Currency> = ArrayList()
    private val baseCurrencyVHList: MutableList<BaseViewHolder<Currency>> = mutableListOf()
    private val conversionCurrencyVHList: MutableList<BaseViewHolder<Currency>> = mutableListOf()
    val clickEvent: BehaviorSubject<Helper> = BehaviorSubject.create()
    var state: Helper.AdapterItemVisibility = Helper.AdapterItemVisibility.INVISIBLE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Currency> {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.list_currency_choice, parent, false)

         return BaseCurrencyVH(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Currency>, position: Int) {
        holder.bind(baseCurrencyList[position], conversionCurrencyList[position])
    }

    override fun getItemCount(): Int {
        return baseCurrencyList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        val currency: Currency = baseCurrencyList[position]

        return when (currency.type) {
            CurrencyType.BASE -> BASE_CURRENCY
            CurrencyType.CONVERSION -> CONVERSION_CURRENCY
            else -> 0
        }
    }

    fun setItems(currencies: List<Currency?>) {
        baseCurrencyList.clear()
        fillCurrencyLists(currencies)
        notifyDataSetChanged()
    }

    fun displayItemAtPositionByState(position: Int, state: Helper.AdapterItemVisibility) {
        makeAllItemsVisible()

        when (state) {
            Helper.AdapterItemVisibility.INVISIBLE -> {
                showItemAtPosition(position)
            }
            Helper.AdapterItemVisibility.VISIBLE -> {
                hideItemAtPosition(position)
            }
        }
    }

    private fun makeAllItemsVisible() {
        baseCurrencyVHList.forEach { viewHolder ->
            viewHolder.itemView.animate().alpha(1f).setDuration(500).start()
        }
    }

    private fun hideItemAtPosition(position: Int) {
        baseCurrencyVHList[position].itemView.animate().alpha(0f).setDuration(500).start()
    }

    private fun showItemAtPosition(position: Int) {
        baseCurrencyVHList[position].itemView.animate().alpha(1f).setDuration(500).start()
    }

    private fun fillCurrencyLists(currencies: List<Currency?>) {
        currencies.forEach { currency ->
            if (currency != null) {

                val duplicateItem: Currency = currency.clone() as Currency
                duplicateItem.type = CurrencyType.BASE
                baseCurrencyList.add(duplicateItem)

                currency.type = CurrencyType.CONVERSION
                conversionCurrencyList.add(currency)
            }
        }
    }

    inner class BaseCurrencyVH(itemView: View) : BaseViewHolder<Currency>(itemView) {

        private val baseCurrencyBtn: MaterialButton = itemView.findViewById(R.id.base_currency_btn)
        private val conversionCurrencyBtn: MaterialButton = itemView.findViewById(R.id.conversion_currency_btn)

        init {
            baseCurrencyBtn.setOnClickListener { onBaseCurrencyBtnClick() }
            conversionCurrencyBtn.setOnClickListener { onConversionCurrencyBtnClick() }
        }

        override fun bind(vararg item: Currency) {
            baseCurrencyBtn.text = item[0].getAbr(context)
            baseCurrencyBtn.icon = item[0].getImage(context)
            conversionCurrencyBtn.text = item[1].getAbr(context)
            conversionCurrencyBtn.icon = item[1].getImage(context)
        }

        private fun onBaseCurrencyBtnClick() {
            clickEvent.onNext(Helper(layoutPosition, baseCurrencyList[layoutPosition], state))
        }

        private fun onConversionCurrencyBtnClick() {
            clickEvent.onNext(Helper(layoutPosition, conversionCurrencyList[layoutPosition], state))
        }
    }

//    inner class ConversionCurrencyVH(itemView: View) : BaseViewHolder<Currency>(itemView) {
//
//        private val parent: ConstraintLayout = itemView.findViewById(R.id.parent)
//        private val conversionCurrencyBtn: MaterialButton =
//            itemView.findViewById(R.id.conversion_currency_btn)
//
//        init {
//
//        }
//
//        override fun bind(item: Currency) {
//            conversionCurrencyBtn.text = item.getAbr(context)
//            conversionCurrencyBtn.icon = item.getImage(context)
//        }
//
//        private fun onConversionBtnClick() {
//            clickEvent.onNext(Helper(layoutPosition, baseCurrencyList[layoutPosition], state))
//        }
//    }

    class Helper(
        var selectedPosition: Int? = null, var selectedCurrency: Currency? = null,
        var state: AdapterItemVisibility? = null
    ) {

        enum class AdapterItemVisibility { INVISIBLE, VISIBLE }

    }

    companion object {
        const val BASE_CURRENCY = 1
        const val CONVERSION_CURRENCY = 2
    }
}