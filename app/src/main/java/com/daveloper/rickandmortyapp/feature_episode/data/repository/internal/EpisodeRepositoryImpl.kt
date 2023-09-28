package com.daveloper.rickandmortyapp.feature_episode.data.repository.internal

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.conversion.PageInfoUtils.toPageInfoData
import com.daveloper.rickandmortyapp.core.utils.numbers.IntUtils.toStringJoinedWithCommas
import com.daveloper.rickandmortyapp.feature_episode.data.network.EpisodeApiService
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.exceptions.EpisodeRepositoryException
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model.EpisodeData
import com.daveloper.rickandmortyapp.feature_episode.utils.conversion.EpisodeUtils.toEpisodeData
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val apiService: EpisodeApiService,
): EpisodeRepository {
    companion object {
        private val TAG = EpisodeRepository::class.java.name
    }

    override suspend fun getEpisodesFromApiByPage(
        pageNumber: Int
    ): RepositoryResult<Pair<PageInfoData?, List<EpisodeData>>> {
        return try {
            if (pageNumber == Constants.INVALID_INT) {
                throw EpisodeRepositoryException
                    .InvalidInputData("The input page number is invalid")
            }
            val result = apiService.getEpisodesByPage(
                page = pageNumber
            ).await()
            if (result.results.isNullOrEmpty()) {
                throw EpisodeRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(
                Pair(
                    result.info?.toPageInfoData(),
                    result.results.mapNotNull { it.toEpisodeData() }
                )

            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override suspend fun getAllEpisodesFromApi(

    ): RepositoryResult<List<EpisodeData>> {
        return try {
            var page: Int = 1
            val allCharacters: MutableList<EpisodeData> = mutableListOf()
            while (page != Constants.INVALID_INT) {
                val resultByPage = getEpisodesFromApiByPage(
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
                throw EpisodeRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(allCharacters)
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override suspend fun getEpisodesByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<EpisodeData>> {
        return try {
            if (ids.isEmpty()) {
                throw EpisodeRepositoryException
                    .InvalidInputData("The input ids list is empty")
            }
            if (ids.size == 1) {
                val result = apiService.getEpisodeById(
                    id = ids.first()
                ).await()
                if (result == null) {
                    throw EpisodeRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    listOf(
                        result.toEpisodeData()
                    ).mapNotNull { it }
                )
            } else {
                val result = apiService.getEpisodesById(
                    ids = ids.toStringJoinedWithCommas()
                ).await()
                if (result.isNullOrEmpty()) {
                    throw EpisodeRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    result.mapNotNull { it.toEpisodeData() }
                )
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }
}