package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.customView.CustomMaterialButton
import com.jay.currencyconverter.model.CurrencySelectWrapper
import com.jay.currencyconverter.model.exchangeRate.currency.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.CurrencyType
import com.jay.currencyconverter.util.ViewState
import io.reactivex.subjects.BehaviorSubject

class CurrencyChoiceAdapter : RecyclerView.Adapter<BaseViewHolder<Currency>>() {

    val clickEvent: BehaviorSubject<CurrencySelectWrapper> = BehaviorSubject.create()

    private val currencyList: MutableList<Currency> = ArrayList()
    private val baseCurrencyBtnList: MutableList<CustomMaterialButton> = mutableListOf()
    private val conversionCurrencyBtnList: MutableList<CustomMaterialButton> = mutableListOf()
    private val visualBehaviour = VisualBehaviour()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Currency> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_currency_choice, parent, false)

        return CurrencyVH(view)
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
        return position
    }

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
            baseCurrencyBtnList.add(baseCurrencyBtn)
            conversionCurrencyBtnList.add(conversionCurrencyBtn)

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
                CurrencySelectWrapper(
                    selectedCurrency = currencyList[layoutPosition],
                    currencyType = CurrencyType.BASE
                )
            )
            visualBehaviour.onBaseCurrencyBtnClick(layoutPosition)
        }

        private fun onConversionCurrencyBtnClick() {
            clickEvent.onNext(
                CurrencySelectWrapper(
                    selectedCurrency = currencyList[layoutPosition],
                    currencyType = CurrencyType.CONVERSION
                )
            )
            visualBehaviour.onConversionCurrencyBtnClick(layoutPosition)
        }
    }


    private inner class VisualBehaviour {

        private lateinit var baseCurrencyBtn: CustomMaterialButton
        private lateinit var conversionCurrencyBtn: CustomMaterialButton

        fun onBaseCurrencyBtnClick(layoutPosition: Int) {
            baseCurrencyBtn = baseCurrencyBtnList[layoutPosition]
            conversionCurrencyBtn = conversionCurrencyBtnList[layoutPosition]

            setViewsBackgroundTint(baseCurrencyBtnList, R.color.colorPrimary)
            conversionCurrencyBtnList.forEach { button -> button.visibility = View.VISIBLE }

            if (baseCurrencyBtn.state == ViewState.ButtonPress.PRESSED){
                setViewBackgroundTint(baseCurrencyBtn, R.color.colorPrimary)
                baseCurrencyBtn.state = ViewState.ButtonPress.NO_PRESSED

            } else {
                baseCurrencyBtnList.forEach { button ->
                    button.state = ViewState.ButtonPress.NO_PRESSED
                }
                setViewBackgroundTint(baseCurrencyBtn, R.color.colorAccent)
                baseCurrencyBtn.state = ViewState.ButtonPress.PRESSED
                conversionCurrencyBtn.visibility = View.INVISIBLE
            }
        }

        fun onConversionCurrencyBtnClick(layoutPosition: Int) {
            baseCurrencyBtn = baseCurrencyBtnList[layoutPosition]
            conversionCurrencyBtn = conversionCurrencyBtnList[layoutPosition]

            setViewsBackgroundTint(conversionCurrencyBtnList, R.color.colorPrimary)
            baseCurrencyBtnList.forEach { button -> button.visibility = View.VISIBLE }

            if (conversionCurrencyBtn.state == ViewState.ButtonPress.PRESSED){
                setViewBackgroundTint(conversionCurrencyBtn, R.color.colorPrimary)
                conversionCurrencyBtn.state = ViewState.ButtonPress.NO_PRESSED

            } else {
                baseCurrencyBtnList.forEach { button ->
                    button.state = ViewState.ButtonPress.NO_PRESSED
                }
                setViewBackgroundTint(conversionCurrencyBtn, R.color.colorAccent)
                conversionCurrencyBtn.state = ViewState.ButtonPress.PRESSED
                baseCurrencyBtn.visibility = View.INVISIBLE
            }
        }

        private fun setViewsBackgroundTint(views: List<View>, color: Int) {
            views.forEach { view ->
                view.backgroundTintList = ContextCompat.getColorStateList(view.context, color)
            }
        }

        private fun setViewBackgroundTint(view: View, color: Int){
            view.backgroundTintList = ContextCompat.getColorStateList(view.context, color)
        }
    }
}