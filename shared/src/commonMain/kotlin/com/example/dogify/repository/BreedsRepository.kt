package com.example.dogify.repository

import com.example.dogify.model.Breed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class BreedsRepository: KoinComponent {

    private val remoteSource: BreedsRemoteSource = get(null)
    private val localSource: BreedsLocalSource = get(null)

    val breeds = localSource.breeds

    suspend fun get() = with(localSource.selectAll()) {
        if (isNullOrEmpty()) {
            return@with fetch()
        } else {
            this
        }
    }

    suspend fun fetch() = supervisorScope {
        remoteSource.getBreeds().map {
            //TODO: Need to change image url
            async { Breed(name = it, imageUrl = "") }
        }.awaitAll().also {
            localSource.clear()
            it.map { async { localSource.insert(it) } }.awaitAll()
        }
    }

    internal suspend fun update(breed: Breed) = localSource.update(breed)
}