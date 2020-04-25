package com.jay.currencyconverter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import com.jay.currencyconverter.util.Animator
import io.reactivex.subjects.BehaviorSubject

class CurrencyChoiceAdapter(private val context: Context) :
    RecyclerView.Adapter<CurrencyChoiceAdapter.ViewHolder>() {

    private val currencyList: MutableList<Currency> = ArrayList()
    private val viewHolderList: MutableList<CurrencyChoiceAdapter.ViewHolder> = mutableListOf()
    val clickEvent: BehaviorSubject<Helper> = BehaviorSubject.create()
    var state: Helper.AdapterItemVisibility = Helper.AdapterItemVisibility.INVISIBLE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyChoiceAdapter.ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.list_currency_choice, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyChoiceAdapter.ViewHolder, position: Int) {
        holder.bind(currencyList[position])
        viewHolderList.add(holder)
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

    fun displayItemAtPositionByState(position: Int, state: Helper.AdapterItemVisibility) {
        makeAllItemsVisible()

        when(state){
            Helper.AdapterItemVisibility.INVISIBLE -> {
                showItemAtPosition(position)
            }
            Helper.AdapterItemVisibility.VISIBLE -> {
                hideItemAtPosition(position)
            }
        }
    }

    private fun makeAllItemsVisible() {
        viewHolderList.forEach { viewHolder ->
            viewHolder.itemView.animate().alpha(1f).setDuration(500).start()
        }
    }

    private fun hideItemAtPosition(position: Int) {
        viewHolderList[position].itemView.animate().alpha(0f).setDuration(500).start()
    }

    private fun showItemAtPosition(position: Int) {
        viewHolderList[position].itemView.animate().alpha(1f).setDuration(500).start()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val currencyButton: MaterialButton = itemView.findViewById(R.id.currency_choice_btn)

        init {
            currencyButton.setOnClickListener { handleCurrencyButtonClick() }
        }

        fun bind(currency: Currency) {
            currencyButton.text = currency.getAbr(context)
            currencyButton.icon = currency.getImage(context)

        }

        private fun handleCurrencyButtonClick() {
            state = if (state == Helper.AdapterItemVisibility.VISIBLE){
                Helper.AdapterItemVisibility.INVISIBLE
            } else{
                Helper.AdapterItemVisibility.VISIBLE
            }
            val button: MaterialButton = viewHolderList[layoutPosition].currencyButton

            for (viewHolder: ViewHolder in viewHolderList) {
                Animator.setViewBackgroundColor(viewHolder.currencyButton, R.color.colorPrimary, context)
            }
            if (state == Helper.AdapterItemVisibility.VISIBLE){
                Animator.setViewBackgroundColor(button, R.color.colorAccent, context)
            }
            clickEvent.onNext(Helper(layoutPosition, currencyList[layoutPosition], state))
        }
    }

    class Helper(var selectedPosition: Int? = null, var selectedCurrency: Currency? = null,
        var state: AdapterItemVisibility? = null) {

        enum class AdapterItemVisibility{ INVISIBLE, VISIBLE }

    }
}