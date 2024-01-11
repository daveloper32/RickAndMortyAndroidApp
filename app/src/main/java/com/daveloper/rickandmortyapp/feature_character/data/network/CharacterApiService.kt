package com.daveloper.rickandmortyapp.feature_character.data.network

import com.daveloper.rickandmortyapp.core.data.network.response.BaseResponseModel
import com.daveloper.rickandmortyapp.feature_character.data.network.model.response.CharacterModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** The [CharacterApiService] interface describes all the api services functions available to make
 * CRUD operations on a Rick & Morty [CharacterModel] data model
 * */
interface CharacterApiService {
    /**Function to get all the Rick & Morty [CharacterModel] on some page from the API.
     *
     * @param page ([Int] type): the number of the page to request the data.
     * @return [BaseResponseModel]<[CharacterModel]>*/
    @GET("character")
    fun getCharactersByPage(
        @Query(value = "page") page: Int = 1
    ): Deferred<BaseResponseModel<CharacterModel>>

    /**Function to get some Rick & Morty [CharacterModel] by id from the API.
     *
     * @param id ([Int] type): the number of the character id to request the data.
     * @return [CharacterModel]*/
    @GET("character/{id}")
    fun getCharacterById(
        @Path(value = "id") id: Int = 1
    ): Deferred<CharacterModel?>

    /**Function to get some Rick & Morty [CharacterModel]'s by ids from the API.
     *
     * @param ids ([String] type): the numbers of the character ids separated by commas to request
     * the data. Ex: 1,2,5,10
     * @return [CharacterModel]*/
    @GET("character/{id}")
    fun getCharactersById(
        @Path(value = "id") ids: String = "1"
    ): Deferred<List<CharacterModel>?>
}