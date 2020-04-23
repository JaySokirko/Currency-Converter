package com.jay.currencyconverter.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.Subject
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import io.reactivex.subjects.BehaviorSubject

class CurrencyChoiceAdapter(private val context: Context) :
    RecyclerView.Adapter<CurrencyChoiceAdapter.ViewHolder>() {

    private val currencyList: MutableList<Currency> = ArrayList()
    private val viewHolderList: MutableList<CurrencyChoiceAdapter.ViewHolder> = mutableListOf()
    val clickEvent: BehaviorSubject<Subject> = BehaviorSubject.create()

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

    fun hideItemAtPosition(position: Int){
        viewHolderList.forEach { viewHolder ->
            viewHolder.itemView.animate().alpha(1f).setDuration(500).start()
        }
        viewHolderList[position].itemView.animate().alpha(0f).setDuration(500).start()
    }

    fun setItems(currencies: List<Currency?>) {
        currencyList.clear()
        currencyList.addAll(currencies.filterNotNull())
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val choiceButton: MaterialButton = itemView.findViewById(R.id.currency_choice_btn)

        init {
            choiceButton.setOnClickListener { handleClickEvent() }
        }

        fun bind(currency: Currency) {
            choiceButton.text = currency.getAbr(context)
            choiceButton.icon = currency.getImage(context)
        }

        private fun handleClickEvent() {
            val button: MaterialButton = viewHolderList[layoutPosition].choiceButton

            for (viewHolder: ViewHolder in viewHolderList) {
                viewHolder.choiceButton.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.colorPrimary)
            }
            button.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.colorAccent)

            clickEvent.onNext(Subject(layoutPosition, currencyList[layoutPosition]))
        }
    }
}