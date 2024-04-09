package com.example.data.source

import com.example.domain.PokemonDetail
import com.example.domain.PokemonDomain

interface PokemonLocalDataSource {

    suspend fun getALLPokemon(): List<PokemonDomain>

    suspend fun getPokemonById(id: Int): PokemonDomain?

    suspend fun savePokemon(pokemon: PokemonDetail)

    suspend fun updatePokemon(pokemon: PokemonDomain): Int
}