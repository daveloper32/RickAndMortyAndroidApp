package com.daveloper.rickandmortyapp.feature_character.data.repository.external.model

import com.daveloper.rickandmortyapp.core.data.repository.model.LocationBasicData
import com.daveloper.rickandmortyapp.core.utils.constants.Constants

data class CharacterData(
    val id: Int = Constants.INVALID_INT,
    val name: String = Constants.EMPTY_STR,
    val status: String = Constants.EMPTY_STR,
    val species: String = Constants.EMPTY_STR,
    val type: String = Constants.EMPTY_STR,
    val gender: String = Constants.EMPTY_STR,
    val origin: LocationBasicData = LocationBasicData(),
    val location: LocationBasicData = LocationBasicData(),
    val image: String = Constants.EMPTY_STR,
    val episodeIds: List<Int> = emptyList(),
    val url: String = Constants.EMPTY_STR,
    val created: String = Constants.EMPTY_STR,
)