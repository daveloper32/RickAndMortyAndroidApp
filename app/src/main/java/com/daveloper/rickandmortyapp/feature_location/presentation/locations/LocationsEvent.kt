package com.daveloper.rickandmortyapp.feature_location.presentation.locations

import com.daveloper.rickandmortyapp.feature_location.domain.enums.LocationFilterType

/** The [LocationsEvent] describe all the possible events that could be launched by
 * [LocationsScreen].
 * */
sealed class LocationsEvent {
    data class Search(
        val query: String
    ): LocationsEvent()

    object ClearSearchBar: LocationsEvent()

    object ActivateFilter: LocationsEvent()

    data class Filter(
        val locationFilterType: LocationFilterType,
        val value: String
    ): LocationsEvent()

    object Refresh: LocationsEvent()
}