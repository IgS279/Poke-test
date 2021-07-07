package com.igs279.pokemon.ui.fav

import android.view.View
import com.igs279.pokemon.data.models.PokeEntityDb

interface OnCustomClickListener {
    fun onItemClick(view: View, item: PokeEntityDb)
}