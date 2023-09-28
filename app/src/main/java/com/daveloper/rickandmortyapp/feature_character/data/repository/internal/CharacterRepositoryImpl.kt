package com.daveloper.rickandmortyapp.feature_character.data.repository.internal

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.numbers.IntUtils.toStringJoinedWithCommas
import com.daveloper.rickandmortyapp.feature_character.data.network.CharacterApiService
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.CharacterData
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.PageInfoData
import com.daveloper.rickandmortyapp.feature_character.utils.conversion.CharacterUtils.toCharacterData
import com.daveloper.rickandmortyapp.feature_character.utils.conversion.PageInfoUtils.toPageInfoData
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService,
): CharacterRepository {
    companion object {
        private val TAG = CharacterRepository::class.java.name
    }

    override suspend fun getCharactersFromApiByPage(
        pageNumber: Int
    ): RepositoryResult<Pair<PageInfoData?, List<CharacterData>>> {
        return try {
            if (pageNumber == Constants.INVALID_INT) {
                throw CharacterRepositoryException
                    .InvalidInputData("The input page number is invalid")
            }
            val result = apiService.getCharactersByPage(
                page = pageNumber
            ).await()
            if (result.results.isNullOrEmpty()) {
                throw CharacterRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(
                Pair(
                    result.info?.toPageInfoData(),
                    result.results.mapNotNull { it.toCharacterData() }
                )

            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override suspend fun getAllCharactersFromApi(

    ): RepositoryResult<List<CharacterData>> {
        return try {
            var page: Int = 1
            val allCharacters: MutableList<CharacterData> = mutableListOf()
            while (page != Constants.INVALID_INT) {
                val resultByPage = getCharactersFromApiByPage(
                    page
                )
                if (resultByPage is RepositoryResult.Success) {
                    if (!resultByPage.data?.second.isNullOrEmpty()) {
                        allCharacters.addAll(resultByPage.data!!.second)
                    }
                    page = resultByPage.data?.first?.nextPage ?: Constants.INVALID_INT
                } else {
                    page = Constants.INVALID_INT
                }
            }
            if (allCharacters.isEmpty()) {
                throw CharacterRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(allCharacters)
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override suspend fun getCharactersByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<CharacterData>> {
        return try {
            if (ids.isEmpty()) {
                throw CharacterRepositoryException
                    .InvalidInputData("The input ids list is empty")
            }
            if (ids.size == 1) {
                val result = apiService.getCharacterById(
                    id = ids.first()
                ).await()
                if (result == null) {
                    throw CharacterRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    listOf(
                        result.toCharacterData()
                    ).mapNotNull { it }
                )
            } else {
                val result = apiService.getCharactersById(
                    ids = ids.toStringJoinedWithCommas()
                ).await()
                if (result.isNullOrEmpty()) {
                    throw CharacterRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    result.mapNotNull { it.toCharacterData() }
                )
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }
}