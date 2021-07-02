package com.igs279.pokemon.data.models

import com.google.gson.annotations.SerializedName


data class SearchPokeResponse (
    val id: Long,
    val name: String,
    val height: Long,
    val weight: Long,
    @SerializedName("base_experience")
    val baseExperience: Long,
    val sprites: Sprites
)

data class Sprites (
    @SerializedName("front_default")
    val frontDefault: String,
)