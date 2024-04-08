package com.example.domain




data class PokemonDetail(
    var sprites: Sprites,
    var abilities: ArrayList<Abilities>,
    var types: ArrayList<Types>,
    var name: String,
    var id: Int,
    var height: Int,
    var weight: Int
)
