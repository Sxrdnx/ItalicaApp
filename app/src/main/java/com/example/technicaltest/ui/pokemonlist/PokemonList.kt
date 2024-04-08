package com.example.technicaltest.ui.pokemonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.data.Resource
import com.example.data.Status
import com.example.domain.PokemonElement
import com.example.technicaltest.databinding.FragmentPokemonListBinding
import com.example.technicaltest.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.technicaltest.utils.Event as UtilsEvent

@AndroidEntryPoint
class PokemonList : Fragment(), PokemonListener {
    @Inject
    lateinit var glide: RequestManager

    private lateinit var pokemonListBinding: FragmentPokemonListBinding
    private val pokemonListViewModel: PokemonListViewModel by viewModels()
    lateinit var adapter: PokemonListAdapter
    var currentOffset = 0
    private var isRequestInProgress = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = PokemonListAdapter(glide, this)
        pokemonListBinding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return pokemonListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonListViewModel.getPokemonList(currentOffset)
        pokemonListBinding.rcyPokemonList.adapter = adapter
        scrollRecycler()


        pokemonListViewModel.pokemonList.observe(viewLifecycleOwner, Observer(::handleUi))
    }

    private fun scrollRecycler() {
        pokemonListBinding.rcyPokemonList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                linearLayoutManager?.let {
                    if (
                        !isRequestInProgress &&
                        it.findLastCompletelyVisibleItemPosition() == (adapter.currentList.size - 1)
                    ) {
                        currentOffset = adapter.currentList.size
                        isRequestInProgress = true
                        pokemonListViewModel.getPokemonList(currentOffset)
                    }
                }

            }
        })
    }

    private fun handleUi(event: UtilsEvent<Resource<List<PokemonElement>>>) {
        event.getContentIfNotHandled()?.let {
            val result = event.peekContent()
            when (result.status) {
                Status.SUCCESS -> {
                    pokemonListBinding.loadingAnimationView.visibility = View.GONE
                    isRequestInProgress = false
                    adapter.submitList(result.data)
                }

                Status.LOADING -> {
                    pokemonListBinding.loadingAnimationView.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    requireActivity().toast(result.message!!)
                }
            }

        }
    }

    override fun onClickElement(id: Int) {
        this.findNavController().navigate(
            PokemonListDirections.actionPokemonListToPokemonDetailFragment(id)
        )
    }


}