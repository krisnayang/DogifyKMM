package com.example.dogify.usecase

import com.example.dogify.model.Breed
import com.example.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class ToggleFavouriteStateUseCase: KoinComponent {

  private val breedsRepository: BreedsRepository = get()

  suspend operator fun invoke(breed: Breed){
    breedsRepository.update(breed.copy(isFavourite = !breed.isFavourite))
  }
}