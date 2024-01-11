package com.daveloper.rickandmortyapp.feature_location.data.network.model.response

import androidx.annotation.Keep
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.google.gson.annotations.SerializedName

/**
 * [LocationModel] base model of a location from API
 * */
@Keep
data class LocationModel(
    @SerializedName("id") val id: Int? = Constants.INVALID_INT,
    @SerializedName("name") val name: String? = Constants.EMPTY_STR,
    @SerializedName("type") val type: String? = Constants.EMPTY_STR,
    @SerializedName("dimension") val dimension: String? = Constants.EMPTY_STR,
    @SerializedName("residents") val residents: List<String>? = emptyList(),
    @SerializedName("url") val url: String? = Constants.EMPTY_STR,
    @SerializedName("created") val created: String? = Constants.EMPTY_STR,
)