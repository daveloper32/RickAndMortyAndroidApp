package com.daveloper.rickandmortyapp.core.data.repository.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants

data class LocationBasicData(
    val name: String = Constants.EMPTY_STR,
    val id: Int = Constants.INVALID_INT,
)