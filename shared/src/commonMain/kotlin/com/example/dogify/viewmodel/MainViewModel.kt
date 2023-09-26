package com.example.dogify.viewmodel

import com.example.dogify.model.Breed
import com.example.dogify.repository.BreedsRepository
import com.example.dogify.usecase.FetchBreedsUseCase
import com.example.dogify.usecase.GetBreedsUseCase
import com.example.dogify.usecase.ToggleFavouriteStateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    breedsRepository: BreedsRepository,
    private val getBreeds: GetBreedsUseCase,
    private val fetchBreeds: FetchBreedsUseCase,
    private val onToggleFavouriteState: ToggleFavouriteStateUseCase
) {
    private val _state = MutableStateFlow(State.LOADING)

    val state: StateFlow<State> = _state

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    enum class State {
        LOADING,
        NORMAL,
        ERROR,
        EMPTY
    }
    private val _events = MutableSharedFlow<Event>()

    val events: SharedFlow<Event> = _events

    enum class Event {
        Error
    }

    private val _shouldFilterFavourites = MutableStateFlow(false)

    val shouldFilterFavourites: StateFlow<Boolean> = _shouldFilterFavourites

    val breeds = breedsRepository.breeds.combine(shouldFilterFavourites) { breeds, shouldFilterFavourites ->
        if (shouldFilterFavourites) {
            breeds.filter { it.isFavourite }
        } else {
            breeds
        }.also {
            _state.value = if (it.isEmpty())
                State.EMPTY else State.NORMAL
        }
    }.stateIn(CoroutineScope(Dispatchers.Default), SharingStarted.WhileSubscribed(),emptyList())
    init {
        loadData()
    }

    fun refresh() {
        loadData(true)
    }

    fun onToggleFavouriteFilter() {
        _shouldFilterFavourites.value = !shouldFilterFavourites.value
    }

    fun onFavouriteTapped(breed: Breed) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                onToggleFavouriteState(breed)
            } catch (e: Exception) {
                _events.emit(Event.Error)
            }
        }
    }

    fun loadData(isForceRefresh: Boolean = false) {
        val getData: suspend () -> List<Breed> = {
            if (isForceRefresh) fetchBreeds.invoke()
            else getBreeds.invoke()
        }
        if (isForceRefresh) {
            _isRefreshing.value = true
        } else {
            _state.value = State.LOADING
        }
        CoroutineScope(Dispatchers.Default).launch {
            _state.value = try {
                getData()
                State.NORMAL
            } catch (e: Exception) {
                State.ERROR
            }
            _isRefreshing.value = false
        }
    }
}