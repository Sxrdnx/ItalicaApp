package com.example.usecases

import com.example.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPokemonById @Inject constructor(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(id: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        withContext(dispatcher) {
            pokemonRepository.getPokemonById(id)
        }

}