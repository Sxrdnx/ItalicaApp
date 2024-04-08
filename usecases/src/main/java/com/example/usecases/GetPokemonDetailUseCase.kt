package com.example.usecases

import com.example.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(private val repository: PokemonRepository) {
    operator fun invoke(url: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) =
        repository.getPokemonDetail(url).flowOn(dispatcher)
}