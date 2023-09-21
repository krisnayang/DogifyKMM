package com.example.dogify.usecase

import com.example.dogify.model.Breed
import com.example.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class GetBreedsUseCase: KoinComponent {
  private val breedsRepository: BreedsRepository = get()
  suspend operator fun invoke(): List<Breed> = breedsRepository.get()
}