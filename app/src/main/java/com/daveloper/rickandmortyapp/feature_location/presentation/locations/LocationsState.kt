package com.daveloper.rickandmortyapp.feature_location.presentation.locations

import com.daveloper.rickandmortyapp.core.domain.model.ItemDataFilter
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location

/**The [LocationsState] describes all [LocationsViewModel] view/screen values.
 *
 * @param isFilterResumeVisible ([Boolean] type)
 * @param isFilterSelectorVisible ([Boolean] type)
 * @param locations ([List]<[Character]> type)
 * @param types ([List]<[ItemDataFilter]> type)
 * @param dimensions ([List]<[ItemDataFilter]> type)
 * @param selectedType ([String] type)
 * @param selectedDimension ([String] type)
 * @param isNotFoundDataVisible ([Boolean] type)
 * @param isRefreshing ([Boolean] type)
 * @param isScrollingUp ([Boolean] type)
 * */
data class LocationsState (
    val isFilterResumeVisible: Boolean = true,
    val isFilterSelectorVisible: Boolean = false,
    val locations: List<Location> = emptyList(),
    val types: List<ItemDataFilter> = emptyList(),
    val dimensions: List<ItemDataFilter> = emptyList(),
    val selectedType: String = Constants.EMPTY_STR,
    val selectedDimension: String = Constants.EMPTY_STR,
    val isNotFoundDataVisible: Boolean = false,
    val isRefreshing: Boolean = false,
    val isScrollingUp: Boolean = false,
)