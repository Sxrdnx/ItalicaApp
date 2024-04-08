package com.example.data.source

import com.example.domain.PokemonDetail
import com.example.domain.PokemonDomain

interface PokemonLocalDataSource {

    suspend fun getALLPokemon(): List<PokemonDomain>

    suspend fun getPokemonById(id: Int): PokemonDomain?

    suspend fun saveAPokemon(pokemon: PokemonDetail)

    fun updatePokemon(pokemon: PokemonDomain): Int
}