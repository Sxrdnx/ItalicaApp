package com.example.domain

data class PokemonElement (
    var name: String,
    var url: String,
    var favorite : Boolean = false,
    val id : Int = 0
    )