package com.igs279.pokemon.data.local

import androidx.lifecycle.LiveData
import com.igs279.pokemon.data.models.PokeEntityDb

class LocalDataSource(private val pokeDAO: PokeDAO) {

    fun getAllPokes(): LiveData<List<PokeEntityDb>> {
        return pokeDAO.getAllLiveData()
    }

    suspend fun insertPoke(pokeEntityDb: PokeEntityDb){
        pokeDAO.insert(pokeEntityDb)
    }

    suspend fun isFavorite(name: String?): Int{
        return pokeDAO.isFavorite(name)
    }

    suspend fun updatePoke(pokeEntityDb: PokeEntityDb){
        pokeDAO.update(pokeEntityDb)
    }

    suspend fun deletePoke(pokeEntityDb: PokeEntityDb){
        pokeDAO.delete(pokeEntityDb)
    }

    suspend fun deleteById(id: Int){
        pokeDAO.deleteById(id)
    }

}