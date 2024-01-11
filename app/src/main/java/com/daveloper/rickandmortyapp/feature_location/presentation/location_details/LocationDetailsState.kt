package com.daveloper.rickandmortyapp.feature_location.presentation.location_details

import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location

/**The [LocationDetailsState] describes all [LocationDetailsViewModel] view/screen values.
 *
 * @param location ([Location] type)
 * @param characters ([List]<[Character]> type)
 * @param isNotFoundDataVisible ([Boolean] type)
 * */
data class LocationDetailsState(
    var location: Location = Location(),
    var characters: List<Character> = emptyList(),
    val isNotFoundDataVisible: Boolean = false,
)