package com.igs279.pokemon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.igs279.pokemon.data.models.PokeEntityDb

@Database(entities = arrayOf(PokeEntityDb::class), version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun myPokeDAO(): PokeDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "poke_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}