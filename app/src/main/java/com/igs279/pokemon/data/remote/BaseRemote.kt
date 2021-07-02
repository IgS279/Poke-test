package com.igs279.pokemon.data.remote

import android.util.Log
import com.igs279.pokemon.TAG
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
                Log.i(TAG, "ResultResp.Success +")
                return ResultResp.Success(response.body()!!)
            } else{
                Log.i(TAG, "ResultResp.Error.ServerError $code")
                return ResultResp.Error.ServerError(code, errorBody)
            }
        } else {
            Log.i(TAG, "Result.Error.NetworkError.....")
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