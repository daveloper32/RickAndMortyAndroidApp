package com.daveloper.rickandmortyapp.core.data.network.response

import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.google.gson.annotations.SerializedName

/**
 * [LocationBasicModel] base model of a basic info from a location on API
 * */
data class LocationBasicModel(
    @SerializedName("name") val name: String? = EMPTY_STR,
    @SerializedName("url") val url: String? = EMPTY_STR,
)
