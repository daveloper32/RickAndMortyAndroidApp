package com.daveloper.rickandmortyapp.feature_location.data.repository.internal

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.conversion.PageInfoUtils.toPageInfoData
import com.daveloper.rickandmortyapp.core.utils.numbers.IntUtils.toStringJoinedWithCommas
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.exceptions.EpisodeRepositoryException
import com.daveloper.rickandmortyapp.feature_location.data.network.LocationApiService
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.LocationRepository
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.model.LocationData
import com.daveloper.rickandmortyapp.feature_location.utils.conversion.LocationUtils.toLocationData
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: LocationApiService,
): LocationRepository {
    companion object {
        private val TAG = LocationRepository::class.java.name
    }

    override suspend fun getLocationsFromApiByPage(
        pageNumber: Int
    ): RepositoryResult<Pair<PageInfoData?, List<LocationData>>> {
        return try {
            if (pageNumber == Constants.INVALID_INT) {
                throw EpisodeRepositoryException
                    .InvalidInputData("The input page number is invalid")
            }
            val result = apiService.getLocationsByPage(
                page = pageNumber
            ).await()
            if (result.results.isNullOrEmpty()) {
                throw EpisodeRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(
                Pair(
                    result.info?.toPageInfoData(),
                    result.results.mapNotNull { it.toLocationData() }
                )

            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override suspend fun getAllLocationsFromApi(

    ): RepositoryResult<List<LocationData>> {
        return try {
            var page: Int = 1
            val allCharacters: MutableList<LocationData> = mutableListOf()
            while (page != Constants.INVALID_INT) {
                val resultByPage = getLocationsFromApiByPage(
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

    override suspend fun getLocationsByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<LocationData>> {
        return try {
            if (ids.isEmpty()) {
                throw EpisodeRepositoryException
                    .InvalidInputData("The input ids list is empty")
            }
            if (ids.size == 1) {
                val result = apiService.getLocationById(
                    id = ids.first()
                ).await()
                if (result == null) {
                    throw EpisodeRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    listOf(
                        result.toLocationData()
                    ).mapNotNull { it }
                )
            } else {
                val result = apiService.getLocationsById(
                    ids = ids.toStringJoinedWithCommas()
                ).await()
                if (result.isNullOrEmpty()) {
                    throw EpisodeRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    result.mapNotNull { it.toLocationData() }
                )
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }
}