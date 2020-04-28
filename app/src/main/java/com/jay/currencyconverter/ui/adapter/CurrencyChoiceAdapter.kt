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

    private val currencyList: MutableList<Currency> = ArrayList()
    private val baseCurrencyVHList: MutableList<BaseViewHolder<Currency>> = mutableListOf()
    private val conversionCurrencyVHList: MutableList<BaseViewHolder<Currency>> = mutableListOf()
    val clickEvent: BehaviorSubject<Helper> = BehaviorSubject.create()
    var state: Helper.AdapterItemVisibility = Helper.AdapterItemVisibility.INVISIBLE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Currency> {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.list_currency_choice, parent, false)

        return when (viewType) {
            BASE_CURRENCY -> {
                BaseCurrencyVH(view)
            }
            CONVERSION_CURRENCY -> {
                ConversionCurrencyVH(view)
            }
            else -> throw IllegalArgumentException("invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Currency>, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        val currency: Currency = currencyList[position]

        return when (currency.type) {
            CurrencyType.BASE -> BASE_CURRENCY
            CurrencyType.CONVERSION -> CONVERSION_CURRENCY
            else -> 0
        }
    }

    fun setItems(currencies: List<Currency?>) {
        currencyList.clear()
        fillCurrencyList(currencies)
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

    /**
     * To fill ViewHolders
     * @see BaseCurrencyVH
     * @see ConversionCurrencyVH
     * need to duplicate each item in
     * @see currencyList
     * Also each item should have own type
     * @see CurrencyType.BASE
     * or
     * @see CurrencyType.CONVERSION
     */
    private fun fillCurrencyList(currencies: List<Currency?>) {
        currencies.forEach { currency ->
            if (currency != null) {

                val duplicateItem: Currency = currency.clone() as Currency
                duplicateItem.type = CurrencyType.BASE
                currencyList.add(duplicateItem)

                currency.type = CurrencyType.CONVERSION
                currencyList.add(currency)
            }
        }
    }

    inner class BaseCurrencyVH(itemView: View) : BaseViewHolder<Currency>(itemView) {

        private val parent: ConstraintLayout = itemView.findViewById(R.id.parent)
        private val baseCurrencyBtn: MaterialButton = itemView.findViewById(R.id.base_currency_btn)

        init {

            baseCurrencyBtn.setOnClickListener { onBaseCurrencyBtnClick() }
        }

        override fun bind(item: Currency) {
            baseCurrencyBtn.text = item.getAbr(context)
            baseCurrencyBtn.icon = item.getImage(context)
        }

        private fun onBaseCurrencyBtnClick() {
            clickEvent.onNext(Helper(layoutPosition, currencyList[layoutPosition], state))
        }
    }

    inner class ConversionCurrencyVH(itemView: View) : BaseViewHolder<Currency>(itemView) {

        private val parent: ConstraintLayout = itemView.findViewById(R.id.parent)
        private val conversionCurrencyBtn: MaterialButton =
            itemView.findViewById(R.id.conversion_currency_btn)

        init {

        }

        override fun bind(item: Currency) {
            conversionCurrencyBtn.text = item.getAbr(context)
            conversionCurrencyBtn.icon = item.getImage(context)
        }

        private fun onConversionBtnClick() {
            clickEvent.onNext(Helper(layoutPosition, currencyList[layoutPosition], state))
        }
    }

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