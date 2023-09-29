package com.daveloper.rickandmortyapp.feature_location.data.network

import com.daveloper.rickandmortyapp.core.data.network.response.BaseResponseModel
import com.daveloper.rickandmortyapp.feature_location.data.network.model.response.LocationModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** The [LocationApiService] interface describes all the api services functions available to make
 * CRUD operations on a Rick & Morty [LocationModel] data model
 * */
interface LocationApiService {
    /**Function to get all the Rick & Morty [LocationModel] on some page from the API.
     *
     * @param page ([Int] type): the number of the page to request the data.
     * @return [BaseResponseModel]<[LocationModel]>*/
    @GET("location")
    fun getLocationsByPage(
        @Query(value = "page") page: Int = 1
    ): Deferred<BaseResponseModel<LocationModel>>

    /**Function to get some Rick & Morty [LocationModel] by id from the API.
     *
     * @param id ([Int] type): the number of the location id to request the data.
     * @return [LocationModel]*/
    @GET("location/{id}")
    fun getLocationById(
        @Path(value = "id") id: Int = 1
    ): Deferred<LocationModel?>

    /**Function to get some Rick & Morty [LocationModel]'s by ids from the API.
     *
     * @param ids ([String] type): the numbers of the location ids separated by commas to request
     * the data. Ex: 1,2,5,10
     * @return [LocationModel]*/
    @GET("location/{id}")
    fun getLocationsById(
        @Path(value = "id") ids: String = "1"
    ): Deferred<List<LocationModel>?>
}