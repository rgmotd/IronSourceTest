package com.example.ironsourcetest.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface ProvideRetrofit {

    fun provideRetrofit(): Retrofit

    class Base(
        private val httpClient: OkHttpClient,
    ) : ProvideRetrofit {

        override fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://s3-us-west-2.amazonaws.com/")
            .client(httpClient)
            .addConverterFactory(
                MoshiConverterFactory.create()
            )
            .build()
    }
}