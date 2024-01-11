package com.daveloper.rickandmortyapp.feature_location.presentation.location_details

/** The [LocationDetailsEvent] describe all the possible events that could be launched by
 * [LocationDetailsScreen].
 * */
sealed class LocationDetailsEvent {
    object OnBack: LocationDetailsEvent()
}
