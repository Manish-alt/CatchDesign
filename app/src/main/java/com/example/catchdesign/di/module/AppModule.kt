package com.example.catchdesign.di.module


import com.example.catchdesign.network.KtorClientProvider
import com.example.catchdesign.repository.MainRepository
import com.example.catchdesign.di.service.ApiService
import com.example.catchdesign.viewModel.MainViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel



val appModule = module {
    single { KtorClientProvider.client }
    single { ApiService(get()) }
    single { MainRepository(get()) }
    viewModel { MainViewModel(get()) }
}