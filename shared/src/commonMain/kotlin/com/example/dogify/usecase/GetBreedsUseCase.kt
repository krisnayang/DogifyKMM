package com.example.dogify.usecase

import com.example.dogify.model.Breed
import com.example.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetBreedsUseCase: KoinComponent {
  private val breedsRepository: BreedsRepository by inject()
  suspend fun invoke(): List<Breed> = breedsRepository.get()
}