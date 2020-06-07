package com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.customView.CustomMaterialButton
import com.jay.currencyconverter.model.CurrencyChoice
import com.jay.currencyconverter.model.exchangeRate.currency.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.CurrencyType
import com.jay.currencyconverter.ui.adapter.viewHolder.BaseViewHolder
import io.reactivex.subjects.BehaviorSubject

class HorizontalCurrencyButtonsAdapter : RecyclerView.Adapter<BaseViewHolder<Currency>>() {

    val clickEvent: BehaviorSubject<CurrencyChoice> = BehaviorSubject.create()

    private val currencyList: MutableList<Currency> = ArrayList()
    private val behaviourManager =
        OnClickBehaviourManager()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Currency> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_horizontal_currency_buttons, parent, false)

        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Currency>, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemId(position: Int): Long =  position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: List<Currency?>) {
        currencyList.clear()
        currencyList.addAll(currencies.filterNotNull())
        notifyDataSetChanged()
    }

    private inner class CurrencyVH(itemView: View) : BaseViewHolder<Currency>(itemView) {

        private val baseCurrencyBtn: CustomMaterialButton
                = itemView.findViewById(R.id.base_currency_btn)

        private val conversionCurrencyBtn: CustomMaterialButton
                = itemView.findViewById(R.id.conversion_currency_btn)

        init {
            behaviourManager.baseButtons.add(baseCurrencyBtn)
            behaviourManager.conversionButtons.add(conversionCurrencyBtn)
            behaviourManager.setup()

            baseCurrencyBtn.setOnClickListener {
                behaviourManager.onBaseButtonClick(it as CustomMaterialButton)
                clickEvent.onNext(CurrencyChoice(currencyList[layoutPosition], CurrencyType.BASE))
            }

            conversionCurrencyBtn.setOnClickListener {
                behaviourManager.onConversionButtonClick(it)
                clickEvent.onNext(CurrencyChoice(currencyList[layoutPosition], CurrencyType.CONVERSION))
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