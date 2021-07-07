package com.igs279.pokemon.ui.fav

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.igs279.pokemon.TAG
import com.igs279.pokemon.data.models.PokeEntityDb
import com.igs279.pokemon.databinding.FavRecyclerItemBinding
import com.squareup.picasso.Picasso

class FavAdapter(private val pokeDbs: List<PokeEntityDb>,
                 private val listener: OnCustomClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: FavRecyclerItemBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
                as FavRecyclerItemBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: FavRecyclerItemBinding = FavRecyclerItemBinding.inflate(
            inflater,
            parent,
            false)
        return ViewHolder(itemView.root)    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                holder.binding.favItemTextViewName.text = pokeDbs[position].pokeEntity.name
                holder.binding.favItemTextViewId.text = pokeDbs[position].pokeEntity.id
                Picasso.get()
                        .load(pokeDbs[position].pokeEntity.imageUrl)
                        .into(holder.binding.favItemImagePoke)
                holder.binding.recyclerImageViewFavPoke.setOnClickListener {
                    Log.i(TAG, "pokeEntityDb = $pokeDbs")
                    listener.onItemClick(it, pokeDbs[position])
                    pokeDbs.toMutableList().removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, pokeDbs.size)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return pokeDbs.size
    }
}