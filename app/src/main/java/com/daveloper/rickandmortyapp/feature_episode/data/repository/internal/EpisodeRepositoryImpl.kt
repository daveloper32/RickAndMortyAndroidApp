package com.daveloper.rickandmortyapp.feature_episode.data.repository.internal

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.conversion.PageInfoUtils.toPageInfoData
import com.daveloper.rickandmortyapp.core.utils.numbers.IntUtils.toStringJoinedWithCommas
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_episode.data.db.dao.EpisodeDao
import com.daveloper.rickandmortyapp.feature_episode.data.db.model.EpisodeEntity
import com.daveloper.rickandmortyapp.feature_episode.data.network.EpisodeApiService
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.exceptions.EpisodeRepositoryException
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model.EpisodeData
import com.daveloper.rickandmortyapp.feature_episode.utils.conversion.data.EpisodeUtils.toEpisodeData
import com.daveloper.rickandmortyapp.feature_episode.utils.conversion.data.EpisodeUtils.toEpisodeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val apiService: EpisodeApiService,
    private val episodeDao: EpisodeDao,
    private val resourceProvider: ResourceProvider,
): EpisodeRepository {
    companion object {
        private val TAG = EpisodeRepository::class.java.name
    }

    override suspend fun getEpisodes(
        requiresRefresh: Boolean
    ): RepositoryResult<List<EpisodeData>> {
        return try {
            if (requiresRefresh) { // Force to get data from local
                val networkDataRequestResult = getAllEpisodesFromApi { _, someData ->
                    saveEpisodesOnLocal(someData)
                }
                if (networkDataRequestResult is RepositoryResult.Error) {
                    getAllEpisodesFromLocal()
                } else {
                    networkDataRequestResult
                }
            } else { // Tries to get data from local
                if (!isLocalDBEmpty()) {
                    getAllEpisodesFromLocal()
                } else {
                    val networkDataRequestResult = getAllEpisodesFromApi { _, someData ->
                        saveEpisodesOnLocal(someData)
                    }
                    if (networkDataRequestResult is RepositoryResult.Error) {
                        getAllEpisodesFromLocal()
                    } else {
                        networkDataRequestResult
                    }
                }
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override fun getEpisodesInRealTime(
        searchQuery: String,
        quantity: Int?
    ): Flow<List<EpisodeData>> {
        return try {
            if (quantity == null) {
                episodeDao
                    .getEpisodes(
                        searchQuery = searchQuery
                    )
                    .mapNotNull { episodes ->
                        episodes.mapNotNull { episode ->
                            episode.toEpisodeData()
                        }
                    }
            } else {
                episodeDao
                    .getEpisodesWithLimit(
                        searchQuery = searchQuery,
                        amount = quantity
                    )
                    .mapNotNull { episodes ->
                        episodes.mapNotNull { episode ->
                            episode.toEpisodeData()
                        }
                    }
            }
        } catch (e: Exception) {
            throw EpisodeRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    override suspend fun searchEpisodesById(
        ids: List<Int>
    ): RepositoryResult<List<EpisodeData>> {
        return try {
            val episodesFromLocal: List<EpisodeEntity>? = episodeDao
                .getEpisodesByIds(id = ids.toIntArray())
            if (episodesFromLocal.isNullOrEmpty()) {
                throw EpisodeRepositoryException
                    .NotFoundData("The data found from Local is null or empty")
            }
            RepositoryResult.Success(
                episodesFromLocal.mapNotNull { it.toEpisodeData() }
            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override fun searchEpisodesByIdInRealTime(
        ids: List<Int>
    ): Flow<List<EpisodeData>> {
        return try {
            episodeDao
                .searchEpisodesByIds(
                    id = ids.toIntArray()
                )
                .mapNotNull { episodes ->
                    episodes.mapNotNull { episode ->
                        episode.toEpisodeData()
                    }
                }
        } catch (e: Exception) {
            throw EpisodeRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    override fun getEpisodeSeasonsInRealTime(): Flow<List<Int>> {
        return try {
            episodeDao
                .getAllSeasons()
        } catch (e: Exception) {
            throw EpisodeRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    /** Function that gets a list of episodes from a page from the API
     * @param pageNumber ([Int] type])
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    private suspend fun getEpisodesFromApiByPage(
        pageNumber: Int
    ): RepositoryResult<Pair<PageInfoData?, List<EpisodeData>>> {
        return try {
            if (pageNumber == Constants.INVALID_INT) {
                throw EpisodeRepositoryException
                    .InvalidInputData("The input page number is invalid")
            }
            if (!resourceProvider.isConnectedToNetwork()) {
                throw EpisodeRepositoryException.NoInternetConnection()
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

    /** Function that gets a list of all episodes from the API
     * @param onDataFromSomePage (Lambda nullable [Unit] function type) -> resolver parameters
     * [PageInfoData]? & [List]<[EpisodeData]>?
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    private suspend fun getAllEpisodesFromApi(
        onDataFromSomePage: ((PageInfoData?, List<EpisodeData>?) -> Unit)? = null
    ): RepositoryResult<List<EpisodeData>> {
        return try {
            if (!resourceProvider.isConnectedToNetwork()) {
                throw EpisodeRepositoryException.NoInternetConnection()
            }
            var page: Int = 1
            val allCharacters: MutableList<EpisodeData> = mutableListOf()
            while (page != Constants.INVALID_INT) {
                val resultByPage = getEpisodesFromApiByPage(
                    page
                )
                if (resultByPage is RepositoryResult.Success) {
                    onDataFromSomePage?.invoke(resultByPage.data?.first, resultByPage.data?.second)
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

    /** Function that gets a list of episodes by ids from the API
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    private suspend fun getEpisodesByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<EpisodeData>> {
        return try {
            if (ids.isEmpty()) {
                throw EpisodeRepositoryException
                    .InvalidInputData("The input ids list is empty")
            }
            if (!resourceProvider.isConnectedToNetwork()) {
                throw EpisodeRepositoryException.NoInternetConnection()
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

    /** Function that gets a list of all episodes from the Local DB
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    private fun getAllEpisodesFromLocal(

    ): RepositoryResult<List<EpisodeData>>  {
        return try {
            val episodesFromLocal: List<EpisodeEntity>? = episodeDao.getEpisodesByIds()
            if (episodesFromLocal.isNullOrEmpty()) {
                throw EpisodeRepositoryException
                    .NotFoundData("The data found from Local is null or empty")
            }
            RepositoryResult.Success(
                episodesFromLocal.mapNotNull { it.toEpisodeData() }
            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of [EpisodeData] and tries to save the valid received data on the
     * local DB after a conversion process to [EpisodeEntity].
     *
     * @param data ([List]<[EpisodeData]>? type)
     * */
    private fun saveEpisodesOnLocal(
        data: List<EpisodeData>?
    ) {
        try {
            data?.let { nonNullData ->
                val dataToSave: List<EpisodeEntity> = nonNullData.mapNotNull {
                    it.toEpisodeEntity()
                }
                episodeDao.insertEpisodes(dataToSave)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /** Function that valid if the local DB do not have any [EpisodeEntity] record
     * @return [Boolean]
     * */
    private fun isLocalDBEmpty(): Boolean = episodeDao.getEpisodesTotal() == 0
}