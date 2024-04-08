package com.example.technicaltest.data.remote

import com.example.data.Resource
import com.example.data.source.PokemonRemoteDataSource
import com.example.domain.PokemonDetail
import com.example.domain.PokemonElement
import com.example.technicaltest.data.remote.response.toPokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RetrofitPokemonDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
)  : PokemonRemoteDataSource {
    override  fun getListPokemon(offset: Int): Flow<Resource<List<PokemonElement>>> = flow {
        try {
            val response = pokemonApi.getListPokemon(25,offset)
            if (response.isSuccessful ){
               emit(Resource.success(response.body()!!.results))
            }else{
               emit( Resource.error(response.message(),null))
            }
        }catch (e: Exception){
            emit(Resource.error("No es posible conectar al servidor. Revisa tu coneccion a internet",null))
        }
    }

    override fun getPokemonDetailByUrl(url: String): Flow<Resource<PokemonDetail>> =  flow {
        try {
            val response = pokemonApi.getPokemonDetail(getId(url = url))
            if (response.isSuccessful ){
                emit(Resource.success(response.body()!!.toPokemonDetail()))
            }else{
                emit( Resource.error(response.message(),null))
            }
        }catch (e: Exception){
            emit(Resource.error("No es posible conectar al servidor. Revisa tu coneccion a internet",null))
        }
    }

    private fun getId(url: String): Int {
        val parts = url.split("/")
        return if (parts.size >= 3) {
            parts[parts.size - 2].toInt()
        } else {
            -1
        }
    }

}