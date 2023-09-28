package com.daveloper.rickandmortyapp.feature_episode.data.network.model.response

import androidx.annotation.Keep
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.google.gson.annotations.SerializedName

/**
 * [EpisodeModel] base model of a episode from API
 * */
@Keep
data class EpisodeModel(
    @SerializedName("id") val id: Int? = Constants.INVALID_INT,
    @SerializedName("name") val name: String? = Constants.EMPTY_STR,
    @SerializedName("air_date") val airDate: String? = Constants.EMPTY_STR,
    @SerializedName("episode") val episode: String? = Constants.EMPTY_STR,
    @SerializedName("characters") val characters: List<String>? = emptyList(),
    @SerializedName("url") val url: String? = Constants.EMPTY_STR,
    @SerializedName("created") val created: String? = Constants.EMPTY_STR,
)
