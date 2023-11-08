package com.daveloper.rickandmortyapp.feature_location.domain
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.LocationRepository
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** The [GetLocationDimensionsUseCase] makes a subscription and get all the [Location] Types
 * saved on real time.
 * */
class GetLocationTypesUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val locationRepository: LocationRepository
) {
    /** The [GetLocationDimensionsUseCase] makes a subscription and get all the [Location] Types
     * saved on real time.
     *
     * @return [Flow]<[List]<[String]>>
     * */
    operator fun invoke(
    ): Flow<List<String>> {
        return try {
            val allValue: String = resourceProvider.getStringResource(R.string.lab_all)
            locationRepository.getLocationTypesInRealTime(
            ).map { types ->
                listOf(allValue) + types
            }
        } catch (e: Exception) {
            flow<List<String>> { emptyList<String>() }
        }
    }
}