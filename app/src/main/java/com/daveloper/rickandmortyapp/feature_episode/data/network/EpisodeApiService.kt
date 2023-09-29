package com.daveloper.rickandmortyapp.feature_episode.data.network

import com.daveloper.rickandmortyapp.core.data.network.response.BaseResponseModel
import com.daveloper.rickandmortyapp.feature_episode.data.network.model.response.EpisodeModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** The [EpisodeApiService] interface describes all the api services functions available to make
 * CRUD operations on a Rick & Morty [EpisodeModel] data model
 * */
interface EpisodeApiService {
    /**Function to get all the Rick & Morty [EpisodeModel] on some page from the API.
     *
     * @param page ([Int] type): the number of the page to request the data.
     * @return [BaseResponseModel]<[EpisodeModel]>*/
    @GET("episode")
    fun getEpisodesByPage(
        @Query(value = "page") page: Int = 1
    ): Deferred<BaseResponseModel<EpisodeModel>>

    /**Function to get some Rick & Morty [EpisodeModel] by id from the API.
     *
     * @param id ([Int] type): the number of the episode id to request the data.
     * @return [EpisodeModel]*/
    @GET("episode/{id}")
    fun getEpisodeById(
        @Path(value = "id") id: Int = 1
    ): Deferred<EpisodeModel?>

    /**Function to get some Rick & Morty [EpisodeModel]'s by ids from the API.
     *
     * @param ids ([String] type): the numbers of the episode ids separated by commas to request
     * the data. Ex: 1,2,5,10
     * @return [EpisodeModel]*/
    @GET("episode/{id}")
    fun getEpisodesById(
        @Path(value = "id") ids: String = "1"
    ): Deferred<List<EpisodeModel>?>
}