package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.customView.CustomMaterialButton
import com.jay.currencyconverter.model.CurrencyChoiceWrapper
import com.jay.currencyconverter.model.exchangeRate.currency.Currency
import com.jay.currencyconverter.model.exchangeRate.currency.CurrencyType
import io.reactivex.subjects.BehaviorSubject

class CurrencyChoiceButtonsAdapter : RecyclerView.Adapter<BaseViewHolder<Currency>>() {

    val clickEvent: BehaviorSubject<CurrencyChoiceWrapper> = BehaviorSubject.create()

    private val currencyList: MutableList<Currency> = ArrayList()
    private val baseCurrencyBtnList: MutableList<CustomMaterialButton> = mutableListOf()
    private val conversionCurrencyBtnList: MutableList<CustomMaterialButton> = mutableListOf()
    private val visualBehaviour = VisualBehaviour()

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
                CurrencyChoiceWrapper(
                    chosenCurrency = currencyList[layoutPosition],
                    currencyType = CurrencyType.BASE
                )
            )
            visualBehaviour.onBaseCurrencyBtnClick(layoutPosition)
        }

        private fun onConversionCurrencyBtnClick() {
            clickEvent.onNext(
                CurrencyChoiceWrapper(
                    chosenCurrency = currencyList[layoutPosition],
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

            if (baseCurrencyBtn.isButtonPressed){
                setViewBackgroundTint(baseCurrencyBtn, R.color.colorPrimary)
                baseCurrencyBtn.isButtonPressed = false

            } else {
                baseCurrencyBtnList.forEach { button ->
                    button.isButtonPressed = false
                }
                setViewBackgroundTint(baseCurrencyBtn, R.color.colorAccent)
                baseCurrencyBtn.isButtonPressed = true
                conversionCurrencyBtn.visibility = View.INVISIBLE
            }
        }

        fun onConversionCurrencyBtnClick(layoutPosition: Int) {
            baseCurrencyBtn = baseCurrencyBtnList[layoutPosition]
            conversionCurrencyBtn = conversionCurrencyBtnList[layoutPosition]

            setViewsBackgroundTint(conversionCurrencyBtnList, R.color.colorPrimary)
            baseCurrencyBtnList.forEach { button -> button.visibility = View.VISIBLE }

            if (conversionCurrencyBtn.isButtonPressed){
                setViewBackgroundTint(conversionCurrencyBtn, R.color.colorPrimary)
                conversionCurrencyBtn.isButtonPressed = false

            } else {
                baseCurrencyBtnList.forEach { button ->
                    button.isButtonPressed = false
                }
                setViewBackgroundTint(conversionCurrencyBtn, R.color.colorAccent)
                conversionCurrencyBtn.isButtonPressed = true
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