package com.jay.currencyconverter.repository.database.nbu

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.model.exchangeRate.NbuCurrency
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class NbuDatabaseManager private constructor() : LifecycleObserver {

    val currenciesToDisplay: PublishSubject<MutableMap<NbuCurrency, Boolean>> = PublishSubject.create()
    private val displayedCurrenciesList: MutableMap<NbuCurrency, Boolean> = mutableMapOf()
    private val disposable = CompositeDisposable()

    init {
        publishDataBaseEntities()
    }

    fun putData(itemsList: List<NbuCurrency>) {

        val subscribe: Disposable = database.getAllSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: List<NbuEntity> ->
                if (result.isEmpty()) {
                    onDatabaseIsEmpty(itemsList)
                } else {
                    onDatabaseIsNotEmpty(itemsList = itemsList, entityList = result)
                }
            }

        disposable.add(subscribe)
    }

    fun update(abbr: String, isShouldDisplayed: Boolean) {
        val subscribe: Disposable =
            Completable.fromAction { database.update(abbr, isShouldDisplayed) }
                .subscribeOn(Schedulers.io())
                .subscribe()

        disposable.add(subscribe)
    }

    fun updateAll(isShouldDisplayed: Boolean){
        val subscribe: Disposable =
            Completable.fromAction { database.updateAll(isShouldDisplayed) }
                .subscribeOn(Schedulers.io())
                .subscribe()

        disposable.add(subscribe)
    }

    private fun onDatabaseIsEmpty(itemsList: List<NbuCurrency>) {
        displayedCurrenciesList.clear()

        val entityList: MutableList<NbuEntity> = mutableListOf()

        itemsList.forEach { currency ->
            entityList.add(
                NbuEntity(
                    currency,
                    isShouldDisplayed = true
                )
            )
            displayedCurrenciesList.put(currency, true)
        }

        insertAll(entityList, onComplete = { currenciesToDisplay.onNext(displayedCurrenciesList) })
    }

    private fun onDatabaseIsNotEmpty(itemsList: List<NbuCurrency>, entityList: List<NbuEntity>) {
        displayedCurrenciesList.clear()

        itemsList.forEach { currency: NbuCurrency ->
            val currentEntity: NbuEntity? =
                entityList.find { currency.currencyAbbreviation == it.currency?.currencyAbbreviation }

            if (currentEntity == null) {
                displayedCurrenciesList.put(currency, true)
            } else {
                displayedCurrenciesList.put(currency, currentEntity.isShouldDisplayed)
            }
        }

        updateDatabase(entityList)
    }

    private fun updateDatabase(entityList: List<NbuEntity>){
        val entityToUpdate: MutableList<NbuEntity> = mutableListOf()
        val entityToInsert: MutableList<NbuEntity> = mutableListOf()

        entityList.forEach { entity: NbuEntity ->
            val currency: NbuCurrency? = displayedCurrenciesList.keys.find {
                it.currencyAbbreviation == entity.currency?.currencyAbbreviation
            }
            if (currency != null) {
                entityToUpdate.add(entity)
            }
        }

        displayedCurrenciesList.keys.forEach {currency ->
            val entity: NbuEntity? = entityList.find {
                it.currency?.currencyAbbreviation == currency.currencyAbbreviation
            }
            if (entity == null){
                entityToInsert.add(
                    NbuEntity(
                        currency,
                        displayedCurrenciesList[currency] ?: false
                    )
                )
            }
        }

        updateAll(entityToUpdate, onComplete = {
            if (entityToInsert.isNotEmpty()) {
                insertAll(entityToInsert, onComplete = null)
            }
        })
    }

    private fun insertAll(entityToInsert: MutableList<NbuEntity>, onComplete: (() -> Unit)? = null) {
        val subscribe: Disposable = Completable.fromAction { database.insertAll(entityToInsert) }
            .subscribeOn(Schedulers.io())
            .subscribe { onComplete?.let { it() } }

        disposable.add(subscribe)
    }

    private fun updateAll(entityToUpdate: List<NbuEntity>, onComplete: (() -> Unit)? = null) {
        val subscribe: Disposable = Completable.fromAction { database.updateAll(entityToUpdate) }
            .subscribeOn(Schedulers.io())
            .subscribe { onComplete?.let { it(); publishDataBaseEntities() } }

        disposable.add(subscribe)
    }

    private fun publishDataBaseEntities() {
        val subscribe: Disposable = database.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: List<NbuEntity> ->

                result.forEach { entity ->
                    val currency: NbuCurrency? = displayedCurrenciesList.keys.find { currency ->
                        entity.currency?.currencyAbbreviation == currency.currencyAbbreviation
                    }
                    if (currency != null){
                        displayedCurrenciesList[currency] = entity.isShouldDisplayed
                    }
                }
                currenciesToDisplay.onNext(displayedCurrenciesList)
            }

        disposable.add(subscribe)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposable.clear()
    }

    companion object {
        private val database: NbuDao = BaseApplication.dataBase.nbuDao()
        val instance = NbuDatabaseManager()
    }
}