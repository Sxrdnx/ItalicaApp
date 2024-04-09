package com.example.usecases

import com.example.data.repository.PokemonRepository
import com.example.domain.PokemonElement
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetPokemonFavorite @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun invoke(
        pokemon: PokemonElement,
        isFavorite: Boolean,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = withContext(dispatcher) {
        repository.setPokemonFavorite(pokemon, isFavorite)
    }
}