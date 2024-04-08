package com.example.data.source

import com.example.data.Resource
import com.example.domain.PokemonDetail
import com.example.domain.PokemonElement
import kotlinx.coroutines.flow.Flow


interface PokemonRemoteDataSource {
     fun getListPokemon(offset: Int): Flow<Resource<List<PokemonElement>>>

     fun getPokemonDetailByUrl(url: String): Flow<Resource<PokemonDetail>>
}