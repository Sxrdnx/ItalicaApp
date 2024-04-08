package com.example.technicaltest.ui.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.PokemonDomain
import com.example.technicaltest.utils.Event
import com.example.usecases.GetPokemonById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonById
) : ViewModel() {
    private val _pokemon = MutableLiveData<Event<PokemonDomain>>()
    val pokemon: LiveData<Event<PokemonDomain>> get() = _pokemon

    fun getPokemon(
        id: Int,
        dispatcher: CoroutineContext = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            _pokemon.postValue(Event(getPokemonDetailUseCase(id)!!))
        }
    }

}