package com.daveloper.rickandmortyapp.feature_location.data.repository.internal

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.data.repository.model.PageInfoData
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.conversion.PageInfoUtils.toPageInfoData
import com.daveloper.rickandmortyapp.core.utils.numbers.IntUtils.toStringJoinedWithCommas
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_location.data.db.dao.LocationDao
import com.daveloper.rickandmortyapp.feature_location.data.db.model.LocationEntity
import com.daveloper.rickandmortyapp.feature_location.data.network.LocationApiService
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.LocationRepository
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.exceptions.LocationRepositoryException
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.model.LocationData
import com.daveloper.rickandmortyapp.feature_location.utils.conversion.data.LocationUtils.toLocationData
import com.daveloper.rickandmortyapp.feature_location.utils.conversion.data.LocationUtils.toLocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: LocationApiService,
    private val locationDao: LocationDao,
    private val resourceProvider: ResourceProvider,
): LocationRepository {
    companion object {
        private val TAG = LocationRepository::class.java.name
    }

    override suspend fun getLocations(
        requiresRefresh: Boolean
    ): RepositoryResult<List<LocationData>> {
        return try {
            if (requiresRefresh) { // Force to get data from local
                val networkDataRequestResult = getAllLocationsFromApi { _, someData ->
                    saveLocationsOnLocal(someData)
                }
                if (networkDataRequestResult is RepositoryResult.Error) {
                    getAllLocationsFromLocal()
                } else {
                    networkDataRequestResult
                }
            } else { // Tries to get data from local
                if (!isLocalDBEmpty()) {
                    getAllLocationsFromLocal()
                } else {
                    val networkDataRequestResult = getAllLocationsFromApi { _, someData ->
                        saveLocationsOnLocal(someData)
                    }
                    if (networkDataRequestResult is RepositoryResult.Error) {
                        getAllLocationsFromLocal()
                    } else {
                        networkDataRequestResult
                    }
                }
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override fun getLocationsInRealTime(
        searchQuery: String
    ): Flow<List<LocationData>> {
        return try {
            locationDao
                .getLocations(
                    searchQuery = searchQuery
                )
                .mapNotNull { locations ->
                    locations.mapNotNull { location ->
                        location.toLocationData()
                    }
                }
        } catch (e: Exception) {
            throw LocationRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    override suspend fun searchLocationsById(
        ids: List<Int>
    ): RepositoryResult<List<LocationData>> {
        return try {
            val locationsFromLocal: List<LocationEntity>? = locationDao
                .getLocationsByIds(id = ids.toIntArray())
            if (locationsFromLocal.isNullOrEmpty()) {
                throw LocationRepositoryException
                    .NotFoundData("The data found from Local is null or empty")
            }
            RepositoryResult.Success(
                locationsFromLocal.mapNotNull { it.toLocationData() }
            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    override fun getLocationTypesInRealTime(): Flow<List<String>> {
        return try {
            locationDao
                .getAllTypes()
                .map { data ->
                    data.filter {
                        it.isNotEmpty()
                    }
                }
        } catch (e: Exception) {
            throw LocationRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    override fun getLocationDimensionsInRealTime(): Flow<List<String>> {
        return try {
            locationDao
                .getAllDimensions()
                .map { data ->
                    data.filter {
                        it.isNotEmpty()
                    }
                }
        } catch (e: Exception) {
            throw LocationRepositoryException.Unknown(
                e.message ?: resourceProvider.getStringResource(R.string.lab_unknown_error)
            )
        }
    }

    /** Function that gets a list of locations from a page from the API
     * @param pageNumber ([Int] type])
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
     private suspend fun getLocationsFromApiByPage(
        pageNumber: Int
    ): RepositoryResult<Pair<PageInfoData?, List<LocationData>>> {
        return try {
            if (pageNumber == Constants.INVALID_INT) {
                throw LocationRepositoryException
                    .InvalidInputData("The input page number is invalid")
            }
            if (!resourceProvider.isConnectedToNetwork()) {
                throw LocationRepositoryException.NoInternetConnection()
            }
            val result = apiService.getLocationsByPage(
                page = pageNumber
            ).await()
            if (result.results.isNullOrEmpty()) {
                throw LocationRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(
                Pair(
                    result.info?.toPageInfoData(),
                    result.results.mapNotNull { it.toLocationData() }
                )

            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of all locations from the API
     * @param onDataFromSomePage (Lambda nullable [Unit] function type) -> resolver parameters
     * [PageInfoData]? & [List]<[LocationData]>?
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    private suspend fun getAllLocationsFromApi(
        onDataFromSomePage: ((PageInfoData?, List<LocationData>?) -> Unit)? = null
    ): RepositoryResult<List<LocationData>> {
        return try {
            if (!resourceProvider.isConnectedToNetwork()) {
                throw LocationRepositoryException.NoInternetConnection()
            }
            var page: Int = 1
            val allCharacters: MutableList<LocationData> = mutableListOf()
            while (page != Constants.INVALID_INT) {
                val resultByPage = getLocationsFromApiByPage(
                    page
                )
                if (resultByPage is RepositoryResult.Success) {
                    onDataFromSomePage?.invoke(resultByPage.data?.first, resultByPage.data?.second)
                    if (!resultByPage.data?.second.isNullOrEmpty()) {
                        allCharacters.addAll(resultByPage.data!!.second)
                    }
                    page = resultByPage.data?.first?.nextPage ?: Constants.INVALID_INT
                } else {
                    page = Constants.INVALID_INT
                }
            }
            if (allCharacters.isEmpty()) {
                throw LocationRepositoryException
                    .NotFoundData("The data found from API is null or empty")
            }
            RepositoryResult.Success(allCharacters)
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of locations by ids from the API
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    private suspend fun getLocationsByIdFromApi(
        ids: List<Int>
    ): RepositoryResult<List<LocationData>> {
        return try {
            if (ids.isEmpty()) {
                throw LocationRepositoryException
                    .InvalidInputData("The input ids list is empty")
            }
            if (!resourceProvider.isConnectedToNetwork()) {
                throw LocationRepositoryException.NoInternetConnection()
            }
            if (ids.size == 1) {
                val result = apiService.getLocationById(
                    id = ids.first()
                ).await()
                if (result == null) {
                    throw LocationRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    listOf(
                        result.toLocationData()
                    ).mapNotNull { it }
                )
            } else {
                val result = apiService.getLocationsById(
                    ids = ids.toStringJoinedWithCommas()
                ).await()
                if (result.isNullOrEmpty()) {
                    throw LocationRepositoryException
                        .NotFoundData("The data found from API is null or empty")
                }
                RepositoryResult.Success(
                    result.mapNotNull { it.toLocationData() }
                )
            }
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of all Location from the Local DB
     * @return [RepositoryResult]<[List]<[LocationData]>>
     * @throws [LocationRepositoryException]*/
    private fun getAllLocationsFromLocal(

    ): RepositoryResult<List<LocationData>>  {
        return try {
            val locationFromLocal: List<LocationEntity>? = locationDao.getLocationsByIds()
            if (locationFromLocal.isNullOrEmpty()) {
                throw LocationRepositoryException
                    .NotFoundData("The data found from Local is null or empty")
            }
            RepositoryResult.Success(
                locationFromLocal.mapNotNull { it.toLocationData() }
            )
        } catch (e: Exception) {
            RepositoryResult.Error(e)
        }
    }

    /** Function that gets a list of [LocationData] and tries to save the valid received data on the
     * local DB after a conversion process to [LocationEntity].
     *
     * @param data ([List]<[LocationData]>? type)
     * */
    private fun saveLocationsOnLocal(
        data: List<LocationData>?
    ) {
        try {
            data?.let { nonNullData ->
                val dataToSave: List<LocationEntity> = nonNullData.mapNotNull {
                    it.toLocationEntity()
                }
                locationDao.insertLocations(dataToSave)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /** Function that valid if the local DB do not have any [LocationEntity] record
     * @return [Boolean]
     * */
    private fun isLocalDBEmpty(): Boolean = locationDao.getLocationsTotal() == 0
}