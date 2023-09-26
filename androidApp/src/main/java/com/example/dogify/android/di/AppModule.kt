package com.example.dogify.android.di

import com.example.dogify.repository.BreedsRepository
import com.example.dogify.usecase.FetchBreedsUseCase
import com.example.dogify.usecase.GetBreedsUseCase
import com.example.dogify.usecase.ToggleFavouriteStateUseCase
import com.example.dogify.viewmodel.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = DI {
//  factory { MainViewModel(get(), get(), get(), get()) }
  val repository = BreedsRepository()
  val getBreedsUseCase = GetBreedsUseCase()
  val fetchBreedsUseCase = FetchBreedsUseCase()
  val toggleFavouriteStateUseCase = ToggleFavouriteStateUseCase()

  bindProvider { MainViewModel(repository, getBreedsUseCase, fetchBreedsUseCase, toggleFavouriteStateUseCase) }
}