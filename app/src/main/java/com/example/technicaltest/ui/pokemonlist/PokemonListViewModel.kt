package com.example.technicaltest.ui.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Resource
import com.example.data.Status
import com.example.data.repository.PokemonRepository
import com.example.domain.PokemonElement
import com.example.domain.toPokemonElement
import com.example.technicaltest.utils.Event
import com.example.usecases.GetPokemonDetailUseCase
import com.example.usecases.GetPokemonListUseCase
import com.example.usecases.SetPokemonFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val setPokemonFavorite: SetPokemonFavorite,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonLits = MutableLiveData<Event<Resource<List<PokemonElement>>>>()

    val pokemonList: LiveData<Event<Resource<List<PokemonElement>>>> get() = _pokemonLits

    private var totalLoaded = 0

    fun getPokemonDatabase(dispatcher: CoroutineContext = Dispatchers.IO) =
        viewModelScope.launch(dispatcher) {
            val result = pokemonRepository.getAllPokemonsLocal().map { it.toPokemonElement() }
            _pokemonLits.postValue(Event(Resource.success(result)))
        }

    fun getPokemonList(offset: Int) {
        viewModelScope.launch {
            _pokemonLits.postValue(Event(Resource.loading(null)))
            getPokemonListUseCase(offset).collect { result ->
                if (result.status == Status.SUCCESS) {
                    val totalRequests = result.data!!.size
                    var succResponse = 0
                    result.data!!.forEach { p ->
                        getPokemonDetailUseCase(p.url).collect { detailResource ->
                            if (detailResource.status == Status.SUCCESS) {
                                succResponse++
                                if (succResponse == totalRequests) {
                                    totalLoaded += totalRequests
                                }
                            }
                        }
                    }
                    getPokemonDatabase()
                } else {
                    _pokemonLits.postValue(
                        Event(
                            Resource.error(
                                "No tienes coneccion a internet ",
                                null
                            )
                        )
                    )
                    getPokemonDatabase()
                }
            }
        }
    }

    fun onFavoriteClick(pokemon: PokemonElement, isFavorite: Boolean) = viewModelScope.launch {
        setPokemonFavorite(pokemon.id, isFavorite)
    }
}