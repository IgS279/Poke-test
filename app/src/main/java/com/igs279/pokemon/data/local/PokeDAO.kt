package com.igs279.pokemon.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.igs279.pokemon.data.models.PokeEntityDb

@Dao
interface PokeDAO {
    @Query("SELECT * FROM PokeEntityDb")
    fun getAllLiveData(): LiveData<List<PokeEntityDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokeEntityDb: PokeEntityDb)

    @Query("SELECT EXISTS (SELECT 1 FROM PokeEntityDb WHERE name =:name)")
    suspend fun isFavorite(name: String?): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pokeEntityDb: PokeEntityDb)

    @Delete
    suspend fun delete(pokeEntityDb: PokeEntityDb?)

    @Query("DELETE FROM PokeEntityDb WHERE idKey = :id")
    suspend fun deleteById(id: Int)
}