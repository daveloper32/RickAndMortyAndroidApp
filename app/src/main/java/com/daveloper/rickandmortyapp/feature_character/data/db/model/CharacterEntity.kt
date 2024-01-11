package com.daveloper.rickandmortyapp.feature_character.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.daveloper.rickandmortyapp.core.data.db.model.LocationBasicEntity
import com.daveloper.rickandmortyapp.core.utils.constants.Constants

@Entity
data class CharacterEntity (
    @PrimaryKey val id: Int = Constants.INVALID_INT,
    val name: String = Constants.EMPTY_STR,
    val status: String = Constants.EMPTY_STR,
    val species: String = Constants.EMPTY_STR,
    val type: String = Constants.EMPTY_STR,
    val gender: String = Constants.EMPTY_STR,
    val origin: LocationBasicEntity = LocationBasicEntity(),
    val location: LocationBasicEntity = LocationBasicEntity(),
    val image: String = Constants.EMPTY_STR,
    val episodeIds: List<Int> = emptyList(),
    val url: String = Constants.EMPTY_STR,
    val created: String = Constants.EMPTY_STR,
)