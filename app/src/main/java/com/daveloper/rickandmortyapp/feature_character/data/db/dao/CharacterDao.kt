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
     * @param searchQuery ([String] type) - Filter results by [CharacterEntity] name, if the query
     * is empty, it returns all the data found in the DB.
     * @return [Flow]<[List]<[CharacterEntity]>>*/
    @Query("SELECT * FROM characterEntity WHERE (:searchQuery = '' OR name LIKE '%' || :searchQuery || '%')")
    fun getCharacters(
        searchQuery: String
    ): Flow<List<CharacterEntity>>

    /**Gets a [Flow] of all [CharacterEntity] saved on the [RickAndMortyDatabase]
     *
     * @param searchQuery ([String] type) - Filter results by [CharacterEntity] name, if the query
     * is empty, it returns all the data found in the DB.
     * @param amount ([Int] type) - Gets just some results.
     * @return [Flow]<[List]<[CharacterEntity]>>*/
    @Query("SELECT * FROM characterEntity WHERE (:searchQuery = '' OR name LIKE '%' || :searchQuery || '%') LIMIT :amount")
    fun getCharactersWithLimit(
        searchQuery: String,
        amount: Int
    ): Flow<List<CharacterEntity>>

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

    /** Filter the [CharacterEntity] table and gets all the unique status ([String]) values
     * @return [Flow]<[List]<[String]>>
     * */
    @Query("SELECT DISTINCT status FROM characterEntity")
    fun getAllStatus(): Flow<List<String>>

    /** Filter the [CharacterEntity] table and gets all the unique species ([String]) values
     * @return [Flow]<[List]<[String]>>
     * */
    @Query("SELECT DISTINCT species FROM characterEntity")
    fun getAllSpecies(): Flow<List<String>>

    /** Filter the [CharacterEntity] table and gets all the unique gender ([String]) values
     * @return [Flow]<[List]<[String]>>
     * */
    @Query("SELECT DISTINCT gender FROM characterEntity")
    fun getAllGenders(): Flow<List<String>>
}