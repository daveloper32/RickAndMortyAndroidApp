package com.daveloper.rickandmortyapp.feature_location.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.LocationRepository
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location
import com.daveloper.rickandmortyapp.feature_location.utils.conversion.domain.LocationUtils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


/** The [GetLocationsUseCase] makes a subscription and get all the [Location] saved on real
 * time.
 * */
class GetLocationsUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val locationRepository: LocationRepository
) {
    /** The [GetLocationsUseCase] makes a subscription and get all the [Location] saved on real
     * time.
     * @param searchQuery ([String] type) -> You can filter the location by [Location] name. By
     * default is an empty string that gets all the [Location].
     * @param type ([String] type) -> You can filter by some [Location] types. By
     * default filter results by all type. If the value is 'all' the filter is not applied.
     * @param dimension ([String] type) -> You can filter by some [Location] dimension. By default
     * filter results by all dimensions. If the value is 'all' the filter is not applied.
     * @param quantity ([Int]? type) - filter and gets the just the amount of results found. If it
     * is null, return all results found.
     * @return [Flow]<[List]<[Location]>>
     * */
    operator fun invoke(
        searchQuery: String = Constants.EMPTY_STR,
        type: String = resourceProvider.getStringResource(R.string.lab_all),
        dimension: String = resourceProvider.getStringResource(R.string.lab_all),
        quantity: Int? = null
    ): Flow<List<Location>> {
        return try {
            locationRepository.getLocationsInRealTime(
                searchQuery,
                quantity
            ).mapNotNull { locations ->
                locations.mapNotNull { location ->
                    location.toDomain()
                }
            }.map { locations -> // Filter by type
                if (
                    type.lowercase() != resourceProvider.getStringResource(R.string.lab_all).lowercase() &&
                    type != Constants.EMPTY_STR
                ) { // Some specific type
                    locations.filter { location ->
                        location.type.lowercase() == type.lowercase()
                    }
                } else { // All types
                    locations
                }
            }.map { locations -> // Filter by dimension
                if (
                    dimension.lowercase() != resourceProvider.getStringResource(R.string.lab_all).lowercase() &&
                    dimension != Constants.EMPTY_STR
                ) { // Some specific dimension
                    locations.filter { location ->
                        location.dimension.lowercase() == dimension.lowercase()
                    }
                } else { // All dimensions
                    locations
                }
            }
        } catch (e: Exception) {
            flow<List<Location>> { emptyList<Location>() }
        }
    }
}