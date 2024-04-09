package com.example.usecases

import com.example.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetPokemonFavorite @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun invoke(
        idPokemon: Int,
        isFavorite: Boolean,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = withContext(dispatcher) {
        repository.setPokemonFavorite(idPokemon, isFavorite)
    }
}