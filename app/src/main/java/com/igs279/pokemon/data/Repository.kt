package com.igs279.pokemon.data


import com.igs279.pokemon.data.remote.RemoteDataSource
import com.igs279.pokemon.data.models.SearchPokeResponse
import com.igs279.pokemon.data.remote.ResultResp

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getSearchPokeData(searchText: String): SearchPokeResponse? {
        return when(val response = remoteDataSource.getSearchPokeData(searchText)){
            is ResultResp.Success -> response.data
            is ResultResp.Error.ServerError -> null
            is ResultResp.Error.NetworkError -> null
        }
    }
}