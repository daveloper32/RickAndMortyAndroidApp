package com.daveloper.rickandmortyapp.feature_episode.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** The [GetEpisodeSeasonsUseCase] makes a subscription and get all the [Episode] Seasons
 * saved on real time.
 * */
class GetEpisodeSeasonsUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val episodeRepository: EpisodeRepository
) {
    /** The [GetEpisodeSeasonsUseCase] makes a subscription and get all the [Episode] Seasons
     * saved on real time.
     *
     * @return [Flow]<[List]<[String]>>
     * */
    operator fun invoke(
    ): Flow<List<String>> {
        return try {
            val allValue: String = resourceProvider.getStringResource(R.string.lab_all)
            episodeRepository.getEpisodeSeasonsInRealTime(
            ).map { seasons ->
                listOf(allValue) + seasons.map { it.toString() }
            }
        } catch (e: Exception) {
            flow<List<String>> { emptyList<String>() }
        }
    }
}