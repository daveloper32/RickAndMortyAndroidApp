package com.daveloper.rickandmortyapp.feature_location.domain.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants

data class Location(
    val id: Int = Constants.INVALID_INT,
    val name: String = Constants.EMPTY_STR,
    val type: String = Constants.EMPTY_STR,
    val dimension: String = Constants.EMPTY_STR,
    val residentIds: List<Int> = emptyList(),
)
