package com.daveloper.rickandmortyapp.core.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

object NetworkUtils {

    /** Function that verifies if the devices have some internet connection at the moment.
     * Note: It requires the ACCESS_NETWORK_STATE permission to use this function.
     *
     * @param ([Context] type)
     * @return [Boolean]
     * */
    fun Context.isConnected(): Boolean {
        return try {
            val connectivityManager: ConnectivityManager = this
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network: Network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities: NetworkCapabilities = connectivityManager
                .getNetworkCapabilities(network) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } catch (e: Exception) {
            false
        }
    }
}