package com.igs279.pokemon.data


import androidx.lifecycle.LiveData
import com.igs279.pokemon.data.local.LocalDataSource
import com.igs279.pokemon.data.models.PokeEntityDb
import com.igs279.pokemon.data.remote.RemoteDataSource
import com.igs279.pokemon.data.models.SearchPokeResponse
import com.igs279.pokemon.data.remote.ResultResp
import com.igs279.pokemon.domain.entities.PokeEntity

class Repository(private val remoteDataSource: RemoteDataSource,
                 private val localDataSource: LocalDataSource) {

    suspend fun getSearchPokeData(searchText: String): PokeEntity? {
        return when(val response = remoteDataSource.getSearchPokeData(searchText)){
            is ResultResp.Success -> {
                savePokeEntity(response.data)
            }
            is ResultResp.Error.ServerError -> PokeEntity("","","","",
                    "","")
            is ResultResp.Error.NetworkError -> PokeEntity("","","","",
                    "","")
        }
    }

    fun getLocalPokeData(): LiveData<List<PokeEntityDb>> {
        return localDataSource.getAllPokes()
    }

    suspend fun insertPoke(pokeEntityDb: PokeEntityDb){
        localDataSource.insertPoke(pokeEntityDb)
    }

    suspend fun isFavorite(name: String?):Int{
        return localDataSource.isFavorite(name)
    }

    suspend fun updatePoke(pokeEntityDb: PokeEntityDb){
        localDataSource.updatePoke(pokeEntityDb)
    }

    suspend fun deletePoke(pokeEntityDb: PokeEntityDb){
        localDataSource.deletePoke(pokeEntityDb)
    }

    suspend fun deleteById(id: Int){
        localDataSource.deleteById(id)
    }

    private fun savePokeEntity(searchPokeResponse: SearchPokeResponse): PokeEntity{
        return PokeEntity(
                id = searchPokeResponse.id.toString(),
                name = searchPokeResponse.name,
                height = searchPokeResponse.height.toString(),
                weight = searchPokeResponse.weight.toString(),
                experience = searchPokeResponse.baseExperience.toString(),
                imageUrl = searchPokeResponse.sprites.frontDefault
        )
    }
}