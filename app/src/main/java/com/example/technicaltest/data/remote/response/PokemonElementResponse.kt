package com.example.technicaltest.data.remote.response

import com.example.domain.PokemonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonElementResponse (
    @SerializedName("results")
    @Expose
    var results: ArrayList<PokemonElement> = arrayListOf(),
    @SerializedName("next")
    @Expose
    var next: String? = null,
)
