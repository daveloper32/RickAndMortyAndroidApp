package com.daveloper.rickandmortyapp.feature_location.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.base.result.enums.MessageResultType
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.LocationRepository
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location
import com.daveloper.rickandmortyapp.feature_location.utils.conversion.domain.LocationUtils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** The [UpdateLocationsUseCase] updates and gets all [Location] data.
 * */
class UpdateLocationsUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val locationRepository: LocationRepository
) {
    /** The [UpdateLocationsUseCase] updates and gets all [Location] data.
     *
     * @param forceDataRefresh ([Boolean] type]) - By default it is set up to false value. This value
     * should only set up to true if expects to refresh all data from Network.
     * @return [UseCaseResult]<[List]<[Location]>>
     * */
    suspend operator fun invoke(
        forceDataRefresh: Boolean = false
    ): UseCaseResult<List<Location>> {
        return try {
            val result = withContext(Dispatchers.IO) {
                locationRepository.getLocations(
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