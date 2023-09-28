package com.daveloper.rickandmortyapp.core.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * [BaseResponseModel] base model of the full data from a paginated list of data service from API.
 *
 * @param results is from [T] any custom type
 * */
@Keep
data class BaseResponseModel <T>(
    @SerializedName("info") val info: InfoModel? = InfoModel(),
    @SerializedName("results") val results: List<T>? = listOf(),
)