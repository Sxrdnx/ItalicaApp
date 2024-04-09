package com.example.technicaltest.ui.pokemonlist

import com.example.domain.PokemonElement

interface PokemonListener {
    fun onClickElement(id: Int)
    fun onFavoriteClickElement(pokemon: PokemonElement, isFavorite: Boolean)
}