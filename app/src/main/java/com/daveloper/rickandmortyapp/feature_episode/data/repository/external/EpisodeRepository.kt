package com.daveloper.rickandmortyapp.feature_episode.data.repository.external

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.exceptions.EpisodeRepositoryException
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model.EpisodeData
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    /** Function that search and gets a list of all episodes.
     *
     * @param requiresRefresh ([Boolean] type]) - By default it is set up to false value. This value
     * should only set up to true if expects to refresh all data from Network.
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    suspend fun getEpisodes(
        requiresRefresh: Boolean = false
    ): RepositoryResult<List<EpisodeData>>

    /** Function that gets a real time list of episodes data from local
     *
     * @param searchQuery ([String] type) - query to filter results (for now by name). By default it
     * is not required and it have an empty string as its value
     * @return [Flow]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    fun getEpisodesInRealTime(
        searchQuery: String = Constants.EMPTY_STR
    ): Flow<List<EpisodeData>>

    /** Function that search and gets a list of episodes by ids from Local
     *
     * @param ids ([List]<[Int]>] type). By default it have an emptyList() value. If the ids list is
     * empty returns all the [EpisodeData] found on local.
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    suspend fun searchEpisodesById(
        ids: List<Int> = emptyList()
    ): RepositoryResult<List<EpisodeData>>
}