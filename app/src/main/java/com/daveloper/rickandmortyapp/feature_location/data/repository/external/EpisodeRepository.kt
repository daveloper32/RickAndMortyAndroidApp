package com.daveloper.rickandmortyapp.feature_location.data.repository.external

import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.exceptions.LocationRepositoryException
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.model.LocationData

interface LocationRepository {
    /** Function that gets a list of locations from a page from the API
     * @param pageNumber ([Int] type])
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    suspend fun getLocationsFromApiByPage(
        pageNumber: Int = 1
    ): RepositoryResult<Pair<PageInfoData?, List<LocationData>>>

    /** Function that gets a list of all locations from the API
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    suspend fun getAllLocationsFromApi(
    ): RepositoryResult<List<LocationData>>

    /** Function that gets a list of locations by ids from the API
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    suspend fun getLocationsByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<LocationData>>
}