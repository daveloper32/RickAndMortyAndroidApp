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

/** The [GetLocationByIdUseCase] search and try to find a [Location] based on a input id [Int].
 * */
class GetLocationByIdUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val locationRepository: LocationRepository,
) {
    /** The [GetLocationByIdUseCase] search and try to find a [Location] based on a input id [Int].
     *
     * @param id ([Int] type) - episode id to try to search.
     * @return [UseCaseResult]<[Location]>
     * */
    suspend operator fun invoke (
        id: Int
    ): UseCaseResult<Location> {
        return try {
            val result = withContext(Dispatchers.IO) {
                locationRepository.searchLocationsById(
                    listOf(
                        id
                    )
                )
            }
            when (result) {
                is RepositoryResult.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        return UseCaseResult.Message(
                            resourceProvider.getStringResource(R.string.lab_not_found_data_error),
                            MessageResultType.ERROR
                        )
                    }
                    val data: Location? = result.data.firstOrNull()?.toDomain()
                    if (data == null) {
                        return UseCaseResult.Message(
                            resourceProvider.getStringResource(R.string.lab_not_found_data_error),
                            MessageResultType.ERROR
                        )
                    }
                    UseCaseResult.Success(
                        data
                    )
                }
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