package com.example.dogify.usecase

import com.example.dogify.model.Breed

class ToggleFavouriteStateUseCase {

  suspend operator fun invoke(breed: Breed) {}
}