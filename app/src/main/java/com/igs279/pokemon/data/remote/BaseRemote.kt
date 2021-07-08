package com.igs279.pokemon.data.remote

import okhttp3.ResponseBody
import retrofit2.Response

open class BaseRemote {

    suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String)
            : ResultResp<T> {
        val response = call.invoke()
        val body = response.body()
        val code = response.code()
        val errorBody = response.errorBody()
        if(response.isSuccessful) {
            if (body != null) {
                return ResultResp.Success(response.body()!!)
            } else{
                return ResultResp.Error.ServerError(code, errorBody)
            }
        } else {
            return ResultResp.Error.NetworkError(errorMessage)
        }
    }
}

sealed class ResultResp<out T: Any> {
    data class Success<out T : Any>(val data: T) : ResultResp<T>()
    sealed class Error : ResultResp<Nothing>(){
        data class ServerError(
            val errorCode: Int = 0,
            val errorBody: ResponseBody? = null
        ) : Error()
        data class NetworkError(val str: String) : Error()
    }
}