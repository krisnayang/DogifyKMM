package com.example.dogify.di

import com.example.dogify.api.BreedsApi
import com.example.dogify.database.createDriver
import com.example.dogify.db.DogifyDatabase
import com.example.dogify.repository.BreedsLocalSource
import com.example.dogify.repository.BreedsRemoteSource
import com.example.dogify.repository.BreedsRepository
import com.example.dogify.usecase.FetchBreedsUseCase
import com.example.dogify.usecase.GetBreedsUseCase
import com.example.dogify.usecase.ToggleFavouriteStateUseCase
import com.example.dogify.util.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val utilityModule = module {
  factory { getDispatcherProvider() }
  single { DogifyDatabase(createDriver("dogify.db")) }
}

private val apiModule = module {
  factory { BreedsApi() }
}

private val repositoryModule = module {
  single { BreedsRepository(get(), get()) }
  factory { BreedsRemoteSource(get(), get()) }
  factory { BreedsLocalSource(get(), get()) }
}

private val usecaseModule = module {
  factory { GetBreedsUseCase() }
  factory { FetchBreedsUseCase() }
  factory { ToggleFavouriteStateUseCase() }
}

private val sharedModules = listOf(usecaseModule, repositoryModule, apiModule, utilityModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(sharedModules)
}