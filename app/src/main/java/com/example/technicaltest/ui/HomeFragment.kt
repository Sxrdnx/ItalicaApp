package com.example.technicaltest.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mylocation.MainActivityLocation
import com.example.technicaltest.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var homeFragmentBindig: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeFragmentBindig = FragmentHomeBinding.inflate(inflater, container, false)
        return homeFragmentBindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFragmentBindig.pokemonFeature.setOnClickListener {
            this.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToPokemonList())
        }

        homeFragmentBindig.locationFeature.setOnClickListener {
            val intent = Intent(requireContext(), MainActivityLocation::class.java)
            startActivity(intent)
        }
    }


}