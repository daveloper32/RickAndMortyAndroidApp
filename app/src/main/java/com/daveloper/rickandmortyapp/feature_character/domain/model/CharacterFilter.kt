package com.daveloper.rickandmortyapp.feature_character.domain.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR

data class CharacterFilter(
    var lifeStatus: String = EMPTY_STR,
    var species: String = EMPTY_STR,
    var gender: String = EMPTY_STR,
)
