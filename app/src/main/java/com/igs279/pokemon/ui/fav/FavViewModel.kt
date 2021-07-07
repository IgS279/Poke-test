package com.igs279.pokemon.ui.fav

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.igs279.pokemon.TAG
import com.igs279.pokemon.data.Repository
import com.igs279.pokemon.data.models.PokeEntityDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(private val repository: Repository) : ViewModel() {

    fun getPokeEntity(): LiveData<List<PokeEntityDb>>{
        Log.i(TAG, "FavViewModel getPokeEntity = ${repository.getLocalPokeData().value}")
        return repository.getLocalPokeData()
    }

    fun deletePoke(pokeEntityDb: PokeEntityDb){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deletePoke(pokeEntityDb)
        }
    }

}