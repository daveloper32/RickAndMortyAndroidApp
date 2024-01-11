package com.daveloper.rickandmortyapp.feature_episode.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.base.result.enums.MessageResultType
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_episode.utils.conversion.domain.EpisodeUtils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** The [UpdateEpisodesUseCase] updates and gets all [Episode] data.
 */
class UpdateEpisodesUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val episodeRepository: EpisodeRepository
) {
    /** The [UpdateEpisodesUseCase] updates and gets all [Episode] data.
     *
     * @param forceDataRefresh ([Boolean] type]) - By default it is set up to false value. This value
     * should only set up to true if expects to refresh all data from Network.
     * @return [UseCaseResult]<[List]<[Episode]>>
     * */
    suspend operator fun invoke(
        forceDataRefresh: Boolean = false
    ): UseCaseResult<List<Episode>> {
        return try {
            val result = withContext(Dispatchers.IO) {
                episodeRepository.getEpisodes(
                    requiresRefresh = forceDataRefresh
                )
            }
            when (result) {
                is RepositoryResult.Success -> UseCaseResult.Success(
                    result.data?.mapNotNull { it.toDomain() }
                )
                is RepositoryResult.Error -> {
                    when (result.exception) {
                        is CharacterRepositoryException.InvalidInputData -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_unknown_error),
                                MessageResultType.ERROR
                            )
                        }
                        is CharacterRepositoryException.NotFoundData -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_not_found_data_error),
                                MessageResultType.ERROR
                            )
                        }
                        is CharacterRepositoryException.NoInternetConnection -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_not_internet_connection),
                                MessageResultType.ERROR
                            )
                        }
                        is CharacterRepositoryException.Unknown -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_unknown_error),
                                MessageResultType.ERROR
                            )
                        }
                        else -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_unknown_error),
                                MessageResultType.ERROR
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            UseCaseResult.Message(
                resourceProvider.getStringResource(R.string.lab_unknown_error),
                MessageResultType.ERROR
            )
        }
    }
}