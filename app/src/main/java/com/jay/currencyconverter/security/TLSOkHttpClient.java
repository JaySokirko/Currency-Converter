package com.jay.currencyconverter.security;

import org.jetbrains.annotations.Nullable;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import okhttp3.OkHttpClient;

public class TLSOkHttpClient {

    @Nullable
    public static OkHttpClient getClient() {
        try {
            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
            if (tlsSocketFactory.getTrustManager() != null) {
                return new OkHttpClient.Builder()
                        .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                        .build();
            }
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

}
