package com.jay.currencyconverter.util.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.util.common.Constant.ABOVE_API_22

object InternetConnection {

    val context: Context = BaseApplication.baseComponent.application.applicationContext

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    @SuppressLint("NewApi")
    fun isInternetConnectionEnabled(): Boolean {
        if (ABOVE_API_22) {
            val network: Network = connectivityManager.activeNetwork ?: return false

            val networkCapabilities: NetworkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo: NetworkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}