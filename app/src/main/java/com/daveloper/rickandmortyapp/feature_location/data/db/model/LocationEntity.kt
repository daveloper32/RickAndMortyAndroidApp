package com.daveloper.rickandmortyapp.feature_location.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.daveloper.rickandmortyapp.core.utils.constants.Constants

@Entity
data class LocationEntity(
    @PrimaryKey val id: Int = Constants.INVALID_INT,
    val name: String = Constants.EMPTY_STR,
    val type: String = Constants.EMPTY_STR,
    val dimension: String = Constants.EMPTY_STR,
    val residentIds: List<Int> = emptyList(),
    val url: String = Constants.EMPTY_STR,
    val created: String = Constants.EMPTY_STR,
)
