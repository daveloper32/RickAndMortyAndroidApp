package com.daveloper.rickandmortyapp.feature_character.domain.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants

data class Character(
    val id: Int = Constants.INVALID_INT,
    val name: String = Constants.EMPTY_STR,
    val lifeStatus: String = Constants.EMPTY_STR,
    val species: String = Constants.EMPTY_STR,
    val type: String = Constants.EMPTY_STR,
    val gender: String = Constants.EMPTY_STR,
    val origin: String = Constants.EMPTY_STR,
    val currentLocation: String = Constants.EMPTY_STR,
    val imageUrl: String = Constants.EMPTY_STR,
    val relatedEpisodeIds: List<Int> = emptyList(),
)
