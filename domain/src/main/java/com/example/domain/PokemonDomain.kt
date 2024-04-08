package com.example.domain



data class PokemonDomain(

    val id: Int = 0,

    val pokemonId: Int = 0,

    val nombre: String = "",

    val sprites: String = "",

    val altura: Int = 0,

    val peso: Int = 0,

    val tipo: ArrayList<String> = arrayListOf(),

    var favorite : Boolean = false

)

