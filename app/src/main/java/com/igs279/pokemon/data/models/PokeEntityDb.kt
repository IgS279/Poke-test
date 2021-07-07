package com.igs279.pokemon.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.igs279.pokemon.domain.entities.PokeEntity

@Entity
data class PokeEntityDb(
    @PrimaryKey//(autoGenerate = true)
    val idKey: Int = 0,
    @Embedded
    //@ColumnInfo(name = "pokeEntity")
    val pokeEntity: PokeEntity
)
