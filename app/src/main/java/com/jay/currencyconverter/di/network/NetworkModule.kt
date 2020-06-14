package com.jay.currencyconverter.di.network

import com.jay.currencyconverter.annotation.OrganizationExchange
import com.jay.currencyconverter.annotation.NbuExchange
import com.jay.currencyconverter.annotation.NbuExchangeByDateAndCurrency
import com.jay.currencyconverter.api.CurrencyExchangeRateApi
import com.jay.currencyconverter.security.TLSOkHttpClient
import com.jay.currencyconverter.util.common.Constant.BANKS_EXCHANGE_URL
import com.jay.currencyconverter.util.common.Constant.NBU_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    private val okHttpClient: OkHttpClient? = TLSOkHttpClient.getClient()

    @OrganizationExchange
    @Provides
    fun provideOrganizationExchange(): CurrencyExchangeRateApi {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BANKS_EXCHANGE_URL)
            .build()
            .create(CurrencyExchangeRateApi::class.java)
    }

    @NbuExchange
    @Provides
    fun provideNbuExchange(): CurrencyExchangeRateApi {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NBU_URL)
            .apply { if (okHttpClient != null) client(okHttpClient) }
            .build()
            .create(CurrencyExchangeRateApi::class.java)
    }

    @NbuExchangeByDateAndCurrency
    @Provides
    fun provideNbuExchangeByDateAndCurrency(): CurrencyExchangeRateApi {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NBU_URL)
            .apply { if (okHttpClient != null) client(okHttpClient) }
            .build()
            .create(CurrencyExchangeRateApi::class.java)
    }
}