package com.example.technicaltest.data.remote.response

import com.example.domain.Abilities
import com.example.domain.PokemonDetail
import com.example.domain.Sprites
import com.example.domain.Types
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PokemonDetailResponse(
    @SerializedName("sprites")
    @Expose
    var sprites: Sprites,
    var abilities: ArrayList<Abilities>,
    var types: ArrayList<Types>,
    var name: String,
    var id: Int,
    var height: Int,
    var weight: Int
)

fun PokemonDetailResponse.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(sprites, abilities, types, name, id, height, weight)
}
