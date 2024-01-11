package com.daveloper.rickandmortyapp.feature_episode.domain

import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_episode.utils.conversion.domain.EpisodeUtils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

/** The [GetEpisodesByIdUseCase] search and try to find a [Episode] based on a input ids [Int]
 * on real time.
 * */
class GetEpisodesByIdUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val episodeRepository: EpisodeRepository
) {
    /** The [GetEpisodeByIdUseCase] search and try to find a [Episode] based on a input ids [Int]
     * on real time.
     *
     * @param ids ([List]<[Int]> type) - episode ids to try to search.
     * @return [Flow]<[List]<[Episode]>>
     * */
    operator fun invoke (
        ids: List<Int> = emptyList()
    ): Flow<List<Episode>> {
        return try {
            episodeRepository.searchEpisodesByIdInRealTime(
                ids
            ).mapNotNull { episodes ->
                episodes.mapNotNull { episode ->
                    episode.toDomain()
                }
            }
        } catch (e: Exception) {
            flow<List<Episode>> { emptyList<Episode>() }
        }
    }
}