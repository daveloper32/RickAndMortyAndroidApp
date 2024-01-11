package com.daveloper.rickandmortyapp.feature_location.data.repository.external

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.exceptions.LocationRepositoryException
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.model.LocationData
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    /** Function that search and gets a list of all Locations.
     *
     * @param requiresRefresh ([Boolean] type]) - By default it is set up to false value. This value
     * should only set up to true if expects to refresh all data from Network.
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    suspend fun getLocations(
        requiresRefresh: Boolean = false
    ): RepositoryResult<List<LocationData>>

    /** Function that gets a real time list of Locations data from local
     *
     * @param searchQuery ([String] type) - query to filter results (for now by name). By default it
     * is not required and it have an empty string as its value
     * @param quantity ([Int]? type) - filter and gets the just the amount of results found. If it
     * is null, return all results found.
     * @return [Flow]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    fun getLocationsInRealTime(
        searchQuery: String = Constants.EMPTY_STR,
        quantity: Int? = null
    ): Flow<List<LocationData>>

    /** Function that search and gets a list of Locations by ids from Local
     *
     * @param ids ([List]<[Int]>] type). By default it have an emptyList() value. If the ids list is
     * empty returns all the [LocationData] found on local.
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    suspend fun searchLocationsById(
        ids: List<Int> = emptyList()
    ): RepositoryResult<List<LocationData>>

    /** Function that gets all the Location Types on real time from local.
     *
     * @return [Flow]<[List]<[String]>>
     * @throws [LocationRepositoryException]*/
    fun getLocationTypesInRealTime(

    ): Flow<List<String>>

    /** Function that gets all the Location Dimensions on real time from local.
     *
     * @return [Flow]<[List]<[String]>>
     * @throws [LocationRepositoryException]*/
    fun getLocationDimensionsInRealTime(

    ): Flow<List<String>>
}