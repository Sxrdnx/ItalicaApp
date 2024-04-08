package com.example.data.repository

import com.example.data.Resource
import com.example.data.Status
import com.example.data.source.PokemonLocalDataSource
import com.example.data.source.PokemonRemoteDataSource
import com.example.domain.PokemonDetail
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
                localDataSource.saveAPokemon(result.data!!)
            }
            result
        }

    suspend fun getAllPokemonsLocal() = localDataSource.getALLPokemon()

    suspend fun getPokemonById(id: Int) = localDataSource.getPokemonById(id)
}