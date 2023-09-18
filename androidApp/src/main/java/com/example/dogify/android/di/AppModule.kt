package com.example.dogify.android.di

import com.example.dogify.android.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { MainViewModel(get(), get(), get(), get()) }
}