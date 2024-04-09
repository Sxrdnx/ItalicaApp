package com.example.data.repository

import com.example.data.Resource
import com.example.data.Status
import com.example.data.source.PokemonLocalDataSource
import com.example.data.source.PokemonRemoteDataSource
import com.example.domain.PokemonDetail
import com.example.domain.PokemonElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) {

    fun getListPokemon(offset: Int) = remoteDataSource.getListPokemon(offset)

    fun getPokemonDetail(url: String): Flow<Resource<PokemonDetail>> =
        remoteDataSource.getPokemonDetailByUrl(url).map { result ->
            if (result.status == Status.SUCCESS) {
                val localData = localDataSource.getPokemonById(result.data!!.id)
                if (localData == null)
                    localDataSource.savePokemon(result.data)
            }
            result
        }

    suspend fun setPokemonFavorite(pokemonElement: PokemonElement, isFavorite: Boolean) {
        localDataSource.getPokemonById(pokemonElement.id)?.copy(favorite = isFavorite)?.let {
            val value = localDataSource.updatePokemon(it)
            println(value)
        }
    }


    suspend fun getAllPokemonsLocal() = localDataSource.getALLPokemon()

    suspend fun getPokemonById(id: Int) = localDataSource.getPokemonById(id)
}