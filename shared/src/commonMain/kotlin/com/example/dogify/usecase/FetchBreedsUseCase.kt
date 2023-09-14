package com.example.dogify.usecase

import com.example.dogify.model.Breed

class FetchBreedsUseCase {

  suspend fun invoke(): List<Breed> = listOf(Breed("Test fetch", ""), )
}