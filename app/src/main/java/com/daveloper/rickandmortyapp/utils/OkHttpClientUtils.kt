package com.daveloper.rickandmortyapp.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientUtils {
    /**
     * Function to get an OkHttpClient
     * - Call timeout:
     * It is the sum of all the time taken to complete the request. It includes time taken in resolving DNS, establishing connection, sending request (including payload) and receiving response (including payload).
     * If there is some time taken in server processing that is also included in this call time.
     * We should configure call timeout to a large value for above said reasons.
     *
     * - Connect timeout:
     * Connection timeout is the time that start from sending the request to a completed TCP handshake with the server. If Retrofit couldn’t establish the connection to the server within the set connection timeout limit, request is considered as failed.
     * A connection timeout may be set large for countries with bad Internet connection.
     *
     * - Read timeout:
     * The read timeout is the time-out applied from the moment you have established a connection (So handshaking is done, and the connection can be used).
     * Specifically, if the server fails to send a byte in specified timeout period after the last byte, a read timeout error will be raised.
     *
     * - Write timeout:
     * If sending a single byte takes longer than the configured write timeout limit the a read timeout error will be raised by retrofit.
     * We can set larger timeouts for users with bad internet connections.*/
    fun getOkHttpAppClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .also {
                // Create a HttpLoggingInterceptor
                val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
                // Set the level to the HttpLoggingInterceptor
                // BODY to all the request
                httpLoggingInterceptor.setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
                // Add the interceptor to the OkHttpClient.Builder()
                it.addInterceptor(httpLoggingInterceptor)
            }
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }
}