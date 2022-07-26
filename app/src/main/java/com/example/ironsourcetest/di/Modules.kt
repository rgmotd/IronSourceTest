package com.example.ironsourcetest.di

import com.example.ironsourcetest.ActionsRepository
import com.example.ironsourcetest.data.ProvideHttpClient
import com.example.ironsourcetest.data.ProvideRetrofit
import com.example.ironsourcetest.core.Mapper
import com.example.ironsourcetest.data.ActionService
import com.example.ironsourcetest.data.ActionSharedPreferences
import com.example.ironsourcetest.presentation.ActionViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { ActionSharedPreferences(androidApplication()) }

    viewModel { ActionViewModel(get(), get(), get()) }
}

val dataModule = module {
    single { ProvideHttpClient.Base().provideHttpClient() }

    single { ProvideRetrofit.Base(get()).provideRetrofit() }

    single { get<Retrofit>().create(ActionService::class.java) }

    factory<ActionsRepository> { ActionsRepository.Base(get(), get()) }

    factory { Mapper.ActionDataToDomainMapper(get()) }

    factory { Mapper.ActionDomainToUiMapper(get()) }

    single { Dispatchers.IO }
}