package com.daveloper.rickandmortyapp.feature_episode.data.repository.external

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model.EpisodeData
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.exceptions.EpisodeRepositoryException

interface EpisodeRepository {
    /** Function that gets a list of episodes from a page from the API
     * @param pageNumber ([Int] type])
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    suspend fun getEpisodesFromApiByPage(
        pageNumber: Int = 1
    ): RepositoryResult<Pair<PageInfoData?, List<EpisodeData>>>

    /** Function that gets a list of all episodes from the API
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    suspend fun getAllEpisodesFromApi(
    ): RepositoryResult<List<EpisodeData>>

    /** Function that gets a list of episodes by ids from the API
     * @return [RepositoryResult]<[List]<[EpisodeData]>>
     * @throws [EpisodeRepositoryException]*/
    suspend fun getEpisodesByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<EpisodeData>>
}