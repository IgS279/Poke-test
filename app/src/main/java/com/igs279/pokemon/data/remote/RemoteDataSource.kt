package com.igs279.pokemon.data.remote

import com.igs279.pokemon.data.models.SearchPokeResponse

class RemoteDataSource : BaseRemote(){

    companion object{
        val instance = RemoteDataSource()
    }

    private val pokeAPI: PokeAPI.PokeApiInterface = PokeAPI.ServiceBuilder
        .getRetrofit()
        .create(PokeAPI.PokeApiInterface::class.java)

    suspend fun getSearchPokeData(searchText: String)
            : ResultResp<SearchPokeResponse> {
        return safeApiResult(
            call = {pokeAPI.getSearchPokeData(searchText)},
            errorMessage = "NetworkError"
        )
    }
}