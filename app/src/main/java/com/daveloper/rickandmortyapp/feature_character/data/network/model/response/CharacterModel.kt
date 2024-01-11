package com.daveloper.rickandmortyapp.feature_character.data.network.model.response

import androidx.annotation.Keep
import com.daveloper.rickandmortyapp.core.data.network.response.LocationBasicModel
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.google.gson.annotations.SerializedName
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.INVALID_INT

/**
 * [CharacterModel] base model of a character from API
 * */
@Keep
data class CharacterModel (
    @SerializedName("id") val id: Int? = INVALID_INT,
    @SerializedName("name") val name: String? = EMPTY_STR,
    @SerializedName("status") val status: String? = EMPTY_STR,
    @SerializedName("species") val species: String? = EMPTY_STR,
    @SerializedName("type") val type: String? = EMPTY_STR,
    @SerializedName("gender") val gender: String? = EMPTY_STR,
    @SerializedName("origin") val origin: LocationBasicModel? = LocationBasicModel(),
    @SerializedName("location") val location: LocationBasicModel? = LocationBasicModel(),
    @SerializedName("created") val created: String? = EMPTY_STR,
    @SerializedName("image") val image: String? = EMPTY_STR,
    @SerializedName("episode") val episode: List<String>? = emptyList(),
    @SerializedName("url") val url: String? = EMPTY_STR,
)