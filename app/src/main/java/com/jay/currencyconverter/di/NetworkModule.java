package com.jay.currencyconverter.di;

import com.jay.currencyconverter.model.currencyExchange.bank.Banks;
import com.jay.currencyconverter.model.currencyExchange.nbu.Nbu;
import com.jay.currencyconverter.service.CurrencyExchangeRateApi;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jay.currencyconverter.util.Constant.BANKS_EXCHANGE_URL;
import static com.jay.currencyconverter.util.Constant.NBU_URL;

@Module
public class NetworkModule {

    @Provides
    public Observable<Banks> provideBankExchange() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BANKS_EXCHANGE_URL)
                .build()
                .create(CurrencyExchangeRateApi.class)
                .getBanksExchangeRate();
    }

    @Provides
    public Observable<List<Nbu>> provideNbuExchange() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NBU_URL)
                .build()
                .create(CurrencyExchangeRateApi.class)
                .getNbuExchangeRate();
    }
}
