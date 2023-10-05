package com.daveloper.rickandmortyapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.daveloper.rickandmortyapp.core.data.db.converters.IntListConverter
import com.daveloper.rickandmortyapp.core.data.db.converters.LocationBasicEntityConverter
import com.daveloper.rickandmortyapp.feature_character.data.db.dao.CharacterDao
import com.daveloper.rickandmortyapp.feature_character.data.db.model.CharacterEntity

/**Base [RickAndMortyDatabase] database class
 * */
@Database(
    entities = [
        CharacterEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        IntListConverter::class,
        LocationBasicEntityConverter::class
    ]
)
abstract class RickAndMortyDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "rick_and_morty_db"
    }
    abstract val characterDao: CharacterDao
}