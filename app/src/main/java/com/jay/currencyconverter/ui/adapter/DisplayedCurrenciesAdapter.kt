package com.jay.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.jakewharton.rxbinding.view.RxView
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.currency.Currencies
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.util.Constant.CHECKED_POSITIONS
import com.jay.currencyconverter.util.GsonConverter
import com.jay.currencyconverter.util.StorageManager
import com.jay.currencyconverter.ui.nbuActivity.NbuCurrenciesDisplay
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
    private val map: MutableMap<Nbu, Boolean> = mutableMapOf()
    private var checkBoxList: MutableList<MaterialCheckBox> = mutableListOf()
    private var checkedPositions: MutableList<Boolean> = mutableListOf()

    private val currencies: Currencies = Currencies()
    private val disposable = CompositeDisposable()
    private val subscription = CompositeSubscription()
    private var jsonConverter = GsonConverter<MutableList<Boolean>>()

    init {
        val subscribe: Disposable = mainCheckboxClickEvent.subscribe { isChecked ->
            checkBoxList.forEach { checkBox -> checkBox.isChecked = isChecked }

            map.keys.forEach { currency -> map[currency] = isChecked }
            NbuCurrenciesDisplay.onChangeDisplayedList(map)

            checkedPositions.clear()
            repeat(itemCount) { checkedPositions.add(isChecked) }
        }
        disposable.add(subscribe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Nbu> {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_currency_choice, parent, false)

        return CurrencyVH(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Nbu>, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemId(position: Int): Long =  position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun setItems(currencies: List<Nbu>) {

        currencyList.clear()
        currencyList.addAll(currencies)

//        checkedPositions.clear()
//        checkedPositions.addAll(currencies.values)

//        map.clear()
//        map.putAll(currencies)

        notifyDataSetChanged()
    }

    private inner class CurrencyVH(itemView: View) : BaseViewHolder<Nbu>(itemView) {

        private val currencyIcon: AppCompatImageView = itemView.findViewById(R.id.currency_image)
        private val currencyAbr: AppCompatTextView = itemView.findViewById(R.id.currency_abr)
        private val currencyName: AppCompatTextView = itemView.findViewById(R.id.currency_name)
        private val checkBox: MaterialCheckBox = itemView.findViewById(R.id.currency_choice_check_box)

        override fun bind(item: Nbu) {

            item.currencyAbbreviation?.let {
                currencyIcon.setImageDrawable(currencies.getCurrencyImageByAbr(it, itemView.context))
            }
            currencyAbr.text = item.currencyAbbreviation
            currencyName.text = item.currencyName

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
                    NbuCurrenciesDisplay.onChangeDisplayedList(item, checkBox.isChecked)
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