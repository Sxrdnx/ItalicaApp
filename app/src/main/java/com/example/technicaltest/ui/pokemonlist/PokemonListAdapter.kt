package com.example.technicaltest.ui.pokemonlist

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.domain.PokemonElement
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ItemPokemonBinding
import com.example.technicaltest.ui.viewcomponent.CircularView

class PokemonListAdapter(
    private val glide: RequestManager,
    private val listener: PokemonListener,
) : ListAdapter<PokemonElement, PokemonListAdapter.ViewHolder>(DiffUtilCallBack) {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var context: Context


    inner class ViewHolder(private val pokemonlistview: ItemPokemonBinding) :
        RecyclerView.ViewHolder(pokemonlistview.root) {
        fun bid(pokemonElement: PokemonElement) = with(pokemonlistview) {
            root.setOnClickListener {
                listener.onClickElement(pokemonElement.id)
            }
            if (pokemonElement.url.isNotEmpty()) {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.pokeball_placeholder)
                    .error(R.drawable.error_background)
                glide
                    .load(pokemonElement.url + "asdasdjsalkdasdjsj")
                    .apply(requestOptions)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            setErrorImage(imageEmploye, pokemonElement.name)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean = false

                    })
                    .into(imageEmploye.getImageView())
                textName.text = pokemonElement.name
            } else
                setErrorImage(imageEmploye, pokemonElement.name)

        }

    }

    private fun setErrorImage(circularView: CircularView, initialsText: String) {
        circularView.setErrorView(
            initialsText = initialsText,
            idTextColor = ContextCompat.getColor(context, R.color.red)
        )
    }

    private object DiffUtilCallBack : DiffUtil.ItemCallback<PokemonElement>() {
        override fun areItemsTheSame(oldItem: PokemonElement, newItem: PokemonElement): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: PokemonElement, newItem: PokemonElement): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPokemonBinding = ItemPokemonBinding.inflate(
            layoutInflater,
            parent, false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bid(pokemon)
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}