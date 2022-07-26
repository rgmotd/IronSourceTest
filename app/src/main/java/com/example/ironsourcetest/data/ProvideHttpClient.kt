package com.example.ironsourcetest.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface ProvideHttpClient {

    fun provideHttpClient(): OkHttpClient

    class Base : ProvideHttpClient {

        override fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    }
}