package com.daveloper.rickandmortyapp.core.data.db.model

import androidx.room.Entity
import com.daveloper.rickandmortyapp.core.utils.constants.Constants

@Entity
data class LocationBasicEntity(
    val name: String = Constants.EMPTY_STR,
    val id: Int = Constants.INVALID_INT,
)
