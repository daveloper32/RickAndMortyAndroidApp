package com.daveloper.rickandmortyapp.feature_character.data.repository.external

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.CharacterData
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
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
     * @return [Flow]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    fun getCharactersInRealTime(

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
}