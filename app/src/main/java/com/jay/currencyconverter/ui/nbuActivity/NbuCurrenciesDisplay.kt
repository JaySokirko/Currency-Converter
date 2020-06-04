package com.jay.currencyconverter.ui.nbuActivity

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.exchangeRate.nbu.Nbu
import com.jay.currencyconverter.repository.room.NbuDao
import com.jay.currencyconverter.repository.room.NbuEntity
import com.jay.currencyconverter.util.Constant.DEFAULT_CURRENCIES_LIST
import com.jay.currencyconverter.util.GsonConverter
import com.jay.currencyconverter.util.StorageManager
import com.jay.currencyconverter.util.TAG
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object NbuCurrenciesDisplay {

    val displayedCurrenciesChanges: MutableLiveData<MutableList<Nbu>> = MutableLiveData()
    val filteredCurrenciesToDisplay: MutableLiveData<MutableMap<Nbu, Boolean>> = MutableLiveData()

    private val currenciesToDisplay: MutableMap<Nbu, Boolean> = LinkedHashMap()

    private val jsonConverter: GsonConverter<MutableMap<Nbu, Boolean>> = GsonConverter()
    private var isShouldBeDisplayed: Boolean = true
    private val database: NbuDao = BaseApplication.dataBase.nbuDao()

    @SuppressLint("CheckResult")
    fun setItems(itemsList: List<Nbu>) {
        currenciesToDisplay.clear()

        val entityList: MutableList<NbuEntity> = mutableListOf()

        val subscribe: Disposable = database.getAllSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: List<NbuEntity>, error: Throwable ->
                entityList.addAll(result)
            }

        when(entityList.isEmpty()){
            true -> {
                itemsList.forEach { nbu ->
                    entityList.add(NbuEntity(nbu.currencyAbbreviation, nbu.currencyName, nbu.rate,
                        isShouldShown = true))
                }
               Single.fromCallable{
                   database.insertAll(entityList)
               }

            }
            false -> {

            }
        }

        database.getAllSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, error ->
                Log.d(TAG, "setItems: " + result)
                Log.d(TAG, "setItems: " + error)
            }

//        val dataFromJson: MutableMap<Nbu, Boolean>? = jsonConverter
//            .getDataFromJson(StorageManager.getVariable(DEFAULT_CURRENCIES_LIST, ""))

//        when(dataFromJson == null) {
//            true -> {
//                itemsList.filterNotNull().forEach { currency ->
//                    currenciesToDisplay.put(currency,
//                        isShouldBeDisplayed
//                    )
//                }
//            }
//            false -> {
//                val keys = dataFromJson.map { it.key.currencyAbbreviation!! }
//                val values = dataFromJson.map { it.value }
//                itemsList.filterNotNull().forEachIndexed { index, currency ->
//
//                     if (keys.contains(currency.currencyAbbreviation)){
//                         currenciesToDisplay.put(currency, values[index])
//                     } else {
//                         currenciesToDisplay.put(currency, true)
//                     }
//                }
//            }
//        }
//        filteredCurrenciesToDisplay.postValue(
//            currenciesToDisplay
//        )
    }

    fun onChangeDisplayedList(currency: Nbu, isShouldBeDisplayed: Boolean) {
        currenciesToDisplay.remove(currency)
        currenciesToDisplay.put(currency, isShouldBeDisplayed)
        saveDisplayedList()
        displayedCurrenciesChanges.postValue(
            currenciesToDisplay.keys.toMutableList())
    }

    fun onChangeDisplayedList(newList: MutableMap<Nbu, Boolean>){
        currenciesToDisplay.clear()
        currenciesToDisplay.putAll(newList)
        saveDisplayedList()
        displayedCurrenciesChanges.postValue(
            currenciesToDisplay.keys.toMutableList())
    }

    private fun saveDisplayedList(){
        StorageManager.saveVariable(DEFAULT_CURRENCIES_LIST,
            jsonConverter.convertDataToJson(
                currenciesToDisplay
            ))
    }
}