package com.daveloper.rickandmortyapp.feature_character.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daveloper.rickandmortyapp.feature_character.data.db.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import com.daveloper.rickandmortyapp.core.data.db.RickAndMortyDatabase

@Dao
interface CharacterDao {
    /**Gets a [Flow] of all [CharacterEntity] saved on the [RickAndMortyDatabase]
     *
     * @return [Flow]<[List]<[CharacterEntity]>>*/
    @Query("SELECT * FROM characterEntity")
    fun getCharacters(): Flow<List<CharacterEntity>>

    /**Search and get a [CharacterEntity] that matches with the input id from the
     * [RickAndMortyDatabase].
     *
     * If is not found returns null.
     *
     * @param id ([Int] type)
     * @return [CharacterEntity]?
     * */
    @Query("SELECT * FROM characterEntity WHERE id = :id")
    suspend fun getCharacterById(
        id: Int
    ): CharacterEntity?

    /**Search and get all the [CharacterEntity] that matches with the input ids from the
     * [RickAndMortyDatabase].
     *
     * @param id (vararg [Int] type)
     * @return [List]<[CharacterEntity]>?
     * */
    @Query("SELECT * FROM characterEntity WHERE id IN (:id)")
    fun getCharactersByIds(
        vararg id: Int
    ): List<CharacterEntity>?

    /**Insert a [CharacterEntity] on the [RickAndMortyDatabase].
     *
     * @param character ([CharacterEntity] type)
     * */
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertCharacter(
        character: CharacterEntity
    )

    /**Insert a [List] of [CharacterEntity] on the [RickAndMortyDatabase].
     *
     * @param characters ([List]<[CharacterEntity]> type)
     * */
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertCharacters(
        characters: List<CharacterEntity>
    )

    /** Gets the total of [CharacterEntity] saved on the [RickAndMortyDatabase].
     *
     * @return [Int]?
     * */
    @Query("SELECT count(*) FROM characterEntity")
    fun getCharactersTotal(): Int?

    /** Deletes an input [CharacterEntity] from the [RickAndMortyDatabase].
     *
     * @param character ([CharacterEntity])
     * */
    @Delete
    fun deleteCharacter(
        character: CharacterEntity
    )

    /** Deletes an all the [CharacterEntity] from the [RickAndMortyDatabase].
     * */
    @Query("DELETE FROM characterEntity")
    fun deleteAllCharacters()
}