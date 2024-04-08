package com.example.technicaltest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringConverter::class)
abstract class PokemonDatabase:RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}