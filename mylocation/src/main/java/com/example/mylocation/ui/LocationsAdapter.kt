package com.example.mylocation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.mylocation.databinding.ItemLocationBinding


class LocationsAdapter: ListAdapter<String,LocationsAdapter.ViewHolder>(DiffUtilCallBack) {
    private lateinit var layoutInflater: LayoutInflater

   inner class ViewHolder(private val locationView: ItemLocationBinding): RecyclerView.ViewHolder(locationView.root){
        fun bid(location: String)=with(locationView){
                txtLocation.text = location
        }
    }

    private object DiffUtilCallBack: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemLocationBinding = ItemLocationBinding.inflate(
            layoutInflater,
            parent,false
        )
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = getItem(position)
        holder.bid(location)
    }

}