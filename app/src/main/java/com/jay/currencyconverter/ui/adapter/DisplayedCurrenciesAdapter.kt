package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.jakewharton.rxbinding.view.RxView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.repository.NbuDatabaseManager
import com.jay.currencyconverter.ui.adapter.viewHolder.BaseViewHolder
import com.jay.currencyconverter.ui.adapter.viewHolder.NbuViewHolder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit


class DisplayedCurrenciesAdapter : RecyclerView.Adapter<BaseViewHolder<Nbu>>(), LifecycleObserver {

    val mainCheckboxClickEvent: PublishSubject<Boolean> = PublishSubject.create()

    private val currencyList: MutableList<Nbu> = mutableListOf()
    private var checkedPositions: MutableList<Boolean> = mutableListOf()
    private var checkBoxList: MutableList<MaterialCheckBox> = mutableListOf()
    private val disposable = CompositeDisposable()
    private val subscription = CompositeSubscription()
    private var nbuDatabaseManager: NbuDatabaseManager = NbuDatabaseManager.instance

    init {
        val subscribe: Disposable = mainCheckboxClickEvent.subscribe { isChecked ->
            checkBoxList.forEach { checkBox -> checkBox.isChecked = isChecked }

            checkedPositions.clear()
            repeat(itemCount) { checkedPositions.add(isChecked) }

            nbuDatabaseManager.updateAll(isChecked)
        }
        disposable.add(subscribe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Nbu> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_currency_choice, parent, false)

        return DisplayedCurrencyVH(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Nbu>, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemId(position: Int): Long =  position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: Map<Nbu, Boolean>) {
        currencyList.clear()
        checkedPositions.clear()

        currencies.forEach {
            currencyList.add(it.key)
            checkedPositions.add(it.value)
        }
        notifyDataSetChanged()
    }

    private inner class DisplayedCurrencyVH(itemView: View) : NbuViewHolder(itemView) {

        override fun bind(item: Nbu) {
            super.bind(item)

            checkBox.isChecked = checkedPositions[layoutPosition]

            checkBoxList.add(checkBox)

            onItemClickListener(item)
        }

        private fun onItemClickListener (item: Nbu){
            val subscribe: Subscription = RxView.clicks(itemView)
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    checkBox.isChecked = !checkBox.isChecked
                    checkedPositions[layoutPosition] = checkBox.isChecked
                    nbuDatabaseManager.update(item.currencyAbbreviation!!, checkBox.isChecked )
                }
            subscription.add(subscribe)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy(){
        disposable.clear()
        subscription.clear()
    }
}