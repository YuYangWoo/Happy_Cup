package com.cookandroid.happycup.data.di

import com.cookandroid.happycup.data.repository.LoginRepository
import com.cookandroid.happycup.data.repository.MainRepository
import com.cookandroid.happycup.ui.main.viewmodel.LoginViewModel
import com.cookandroid.happycup.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { LoginRepository() }
    single { MainRepository() }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
}
