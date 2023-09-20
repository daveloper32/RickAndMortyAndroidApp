package com.daveloper.rickandmortyapp.utils

import com.daveloper.rickandmortyapp.utils.OkHttpClientUtils.getOkHttpAppClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {
    /**Function to create a retrofit instance [Retrofit] for a base URL [String], adds a GSON converter
     * deserializer and add a client with an interceptor based on the wantsAHttpClient [Boolean] value
     * */
    inline fun <reified T> createWebService (
        baseURL: String,
        wantsAHttpClient: Boolean = true
    ): T {
        // Init a Retrofit instance
        val retrofit: Retrofit =
            if (wantsAHttpClient) { // Verify if the input wantsAHttpClient var is true
                Retrofit.Builder()
                    .baseUrl(baseURL) // Add the base url to call their services
                    .addConverterFactory(GsonConverterFactory.create()) // GSON Deserializer
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(getOkHttpAppClient()) // Assign the default OkHttpClient with an interceptor to the Retrofit instance
                    .build()
            } else { // Verify if the input wantsAHttpClient var is false
                Retrofit.Builder()
                    .baseUrl(baseURL) // Add the base url to call their services
                    .addConverterFactory(GsonConverterFactory.create()) // GSON Deserializer
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
            }
        return retrofit.create(T::class.java) // Return the retrofit instance based on the reified input T class type
    }
}