package com.daveloper.rickandmortyapp.feature_character.data.repository.external

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.CharacterData
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    /** Function that search and gets a list of all characters.
     *
     * @param requiresRefresh ([Boolean] type]) - By default it is set up to false value. This value
     * should only set up to true if expects to refresh all data from Network.
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    suspend fun getCharacters(
        requiresRefresh: Boolean = false
    ): RepositoryResult<List<CharacterData>>

    /** Function that gets a real time list of characters data from local
     *
     * @param searchQuery ([String] type) - query to filter results (for now by name). By default it
     * is not required and it have an empty string as its value
     * @param quantity ([Int]? type) - filter and gets the just the amount of results found. If it
     * is null, return all results found.
     * @return [Flow]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    fun getCharactersInRealTime(
        searchQuery: String = Constants.EMPTY_STR,
        quantity: Int? = null
    ): Flow<List<CharacterData>>

    /** Function that search and gets a list of characters by ids from Local
     *
     * @param ids ([List]<[Int]>] type). By default it have an emptyList() value. If the ids list is
     * empty returns all the [CharacterData] found on local.
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    suspend fun searchCharactersById(
        ids: List<Int> = emptyList()
    ): RepositoryResult<List<CharacterData>>

    /** Function that gets all the Character Life Status on real time from local.
     *
     * @return [Flow]<[List]<[String]>>
     * @throws [CharacterRepositoryException]*/
    fun getCharacterLifeStatusInRealTime(

    ): Flow<List<String>>

    /** Function that gets all the Character Species on real time from local.
     *
     * @return [Flow]<[List]<[String]>>
     * @throws [CharacterRepositoryException]*/
    fun getCharacterSpeciesInRealTime(

    ): Flow<List<String>>

    /** Function that gets all the Character Genders on real time from local.
     *
     * @return [Flow]<[List]<[String]>>
     * @throws [CharacterRepositoryException]*/
    fun getCharacterGendersInRealTime(

    ): Flow<List<String>>
}