package com.example.usecases

import com.example.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(offset: Int, dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        pokemonRepository.getListPokemon(offset).flowOn(dispatcher)
}