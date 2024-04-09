package com.example.technicaltest.ui.pokemondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.domain.PokemonDomain
import com.example.technicaltest.databinding.FragmentPokemonDetailBinding
import com.example.technicaltest.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private lateinit var pokemonDetailBinding: FragmentPokemonDetailBinding
    private val args: PokemonDetailFragmentArgs by navArgs()
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        pokemonDetailViewModel.getPokemon(args.idPokemon)
        pokemonDetailBinding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return pokemonDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonDetailViewModel.pokemon.observe(viewLifecycleOwner, Observer(::handleEvent))
        pokemonDetailBinding.imageSave.setOnCheckedChangeListener { _, isFavorite ->
            pokemonDetailViewModel.onFavoriteClick(args.idPokemon, isFavorite)
        }
    }

    fun handleEvent(event: Event<PokemonDomain>) {
        event.getContentIfNotHandled()?.let { pokemon ->
            pokemonDetailBinding.imageSave.isChecked = pokemon.favorite
            pokemonDetailBinding.textName.text = pokemon.nombre
            pokemonDetailBinding.txtHeight.text = pokemon.altura.toString()
            pokemonDetailBinding.txtWeight.text = pokemon.peso.toString()
            glide.load(pokemon.sprites).into(pokemonDetailBinding.photo)
        }
    }

}