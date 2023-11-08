package com.daveloper.rickandmortyapp.feature_episode.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_episode.utils.domain.EpisodeUtils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

/** The [GetEpisodesUseCase] makes a subscription and get all the [Episode] saved on real
 * time.
 * */
class GetEpisodesUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val episodeRepository: EpisodeRepository
) {
    /** The [GetEpisodesUseCase] makes a subscription and get all the [Episode] saved on real
     * time.
     * @param searchQuery ([String] type) -> You can filter the characters by [Episode] name. By
     * default is an empty string that gets all the [Episode].
     * @param season ([String] type) -> You can filter by some [Episode] season. By
     * default filter results by all seasons. If the value is 'all' the filter is not applied.
     * @return [Flow]<[List]<[Episode]>>
     * */
    operator fun invoke(
        searchQuery: String = Constants.EMPTY_STR,
        season: String = resourceProvider.getStringResource(R.string.lab_all),
    ): Flow<List<Episode>> {
        return try {
            episodeRepository.getEpisodesInRealTime(
                searchQuery
            ).mapNotNull { episodes ->
                episodes.mapNotNull { episode ->
                    episode.toDomain()
                }
            }.map { episodes -> // Filter by season
                if (
                    season.lowercase() != resourceProvider.getStringResource(R.string.lab_all).lowercase() &&
                    season != Constants.EMPTY_STR
                ) { // Some specific life status
                    val seasonValue: Int? = season.toIntOrNull()
                    if (seasonValue != null) { // Filter by specific season number
                        episodes.filter { episode ->
                            episode.seasonNumber == seasonValue
                        }
                    } else { // All seasons
                        episodes
                    }
                } else { // All seasons
                    episodes
                }
            }
        } catch (e: Exception) {
            flow<List<Episode>> { emptyList<Episode>() }
        }
    }
}