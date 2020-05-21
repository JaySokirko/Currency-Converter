package com.jay.currencyconverter.di;

import com.jay.currencyconverter.model.currencyExchange.bank.Banks;
import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu;

import java.util.List;

import dagger.Component;
import io.reactivex.Observable;

@Component(modules = {NetworkModule.class})
public interface NetworkComponent {

    Observable<Banks> getBankExchange();

    Observable<List<Nbu>> getNbuExchange();
}
