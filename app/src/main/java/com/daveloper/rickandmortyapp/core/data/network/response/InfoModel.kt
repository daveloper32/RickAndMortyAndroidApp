package com.daveloper.rickandmortyapp.core.data.network.response

import androidx.annotation.Keep
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.INVALID_INT
import com.google.gson.annotations.SerializedName

/**
 * [InfoModel] base model of the info from a paginated list of data service from API
 * */
@Keep
data class InfoModel(
    @SerializedName("count") val count: Int = INVALID_INT,
    @SerializedName("pages") val pages: Int = INVALID_INT,
    @SerializedName("next") val nextPage: String? = EMPTY_STR,
    @SerializedName("prev") val previousPage: String? = EMPTY_STR,
)
