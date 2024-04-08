package com.example.technicaltest.data.local

import com.example.data.source.PokemonLocalDataSource
import com.example.domain.PokemonDetail
import com.example.domain.PokemonDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomPokemonDatasource @Inject constructor(private val pokemonDao: PokemonDao) :
    PokemonLocalDataSource {
    override suspend fun getALLPokemon(): List<PokemonDomain> {
        return withContext(Dispatchers.IO) {
            try {
                pokemonDao.getPokemonOffline().map { it.toPokemonDomain() }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun getPokemonById(id: Int): PokemonDomain? {
        return withContext(Dispatchers.IO) {
            try {
                pokemonDao.getPokemonById(id)?.toPokemonDomain()
            } catch (e: Exception) {
                PokemonDomain()
            }
        }
    }

    override suspend fun saveAPokemon(pokemon: PokemonDetail) {
        pokemonDao.savePokemon(pokemon = pokemon.toPokemonEntity())
    }


    override fun updatePokemon(pokemon: PokemonDomain): Int {
        TODO("Not yet implemented")
    }

    fun PokemonDetail.toPokemonEntity(): PokemonEntity {
        return PokemonEntity(
            pokemonId = id,
            nombre = name,
            sprites = sprites.front_default,
            altura = height,
            peso = weight,
            tipo = types.map { it.type.name } as ArrayList<String>)
    }


}