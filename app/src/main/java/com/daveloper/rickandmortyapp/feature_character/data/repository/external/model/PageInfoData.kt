package com.daveloper.rickandmortyapp.feature_character.data.repository.external.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants

data class PageInfoData(
    val count: Int = Constants.INVALID_INT,
    val pages: Int = Constants.INVALID_INT,
    val nextPage: Int = Constants.INVALID_INT,
    val previousPage: Int = Constants.INVALID_INT,
)
