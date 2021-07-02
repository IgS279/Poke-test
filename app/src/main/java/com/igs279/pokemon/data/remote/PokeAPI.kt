package com.igs279.pokemon.data.remote

import com.igs279.pokemon.data.models.SearchPokeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class PokeAPI {

    object ServiceBuilder {

        private val interceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun getRetrofit(): Retrofit {
            return retrofit
        }
    }

    interface PokeApiInterface{
        @GET("{name}")
        suspend fun getSearchPokeData(
            @Path("name") name: String
        ) : Response<SearchPokeResponse>
    }
}