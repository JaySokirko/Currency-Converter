package com.jay.currencyconverter.viewmodel

import com.jay.currencyconverter.model.currencyExchange.Organization
import com.jay.currencyconverter.service.CurrencyExchangeRateApi
import io.reactivex.Observable

class BanksExchange {

    fun execute() : Observable<List<Organization>> {
       return Observable.create { observableEmitter ->
           CurrencyExchangeRateApi
               .create()
               .getBanksExchangeRate()
               .subscribe({ result ->
                   observableEmitter.onNext(result.organizations!!)
               }, { error ->
                   observableEmitter.onError(error)
               })
       }
    }
}