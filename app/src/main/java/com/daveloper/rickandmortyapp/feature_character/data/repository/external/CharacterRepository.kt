package com.daveloper.rickandmortyapp.feature_character.data.repository.external

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.CharacterData
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.PageInfoData

interface CharacterRepository {
    /** Function that gets a list of characters from a page from the API
     * @param pageNumber ([Int] type])
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    suspend fun getCharactersFromApiByPage(
        pageNumber: Int = 1
    ): RepositoryResult<Pair<PageInfoData?, List<CharacterData>>>

    /** Function that gets a list of all characters from the API
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    suspend fun getAllCharactersFromApi(
    ): RepositoryResult<List<CharacterData>>

    /** Function that gets a list of characters by ids from the API
     * @return [RepositoryResult]<[List]<[CharacterData]>>
     * @throws [CharacterRepositoryException]*/
    suspend fun getCharactersByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<CharacterData>>
}