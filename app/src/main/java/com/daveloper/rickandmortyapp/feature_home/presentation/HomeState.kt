package com.daveloper.rickandmortyapp.feature_home.presentation

import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location

/**The [HomeState] describes all [HomeViewModel] view/screen values.
 *
 * @param characters ([List]<[Character]> type)
 * @param characters ([List]<[Episode]> type)
 * @param characters ([List]<[Location]> type)
 * @param isNotFoundCharacterDataVisible ([Boolean] type)
 * @param isNotFoundEpisodeDataVisible ([Boolean] type)
 * @param isNotFoundLocationDataVisible ([Boolean] type)
 * @param isRefreshing ([Boolean] type)
 * @param isScrollingUp ([Boolean] type)
 * @param isScrollUpButtonVisible ([Boolean] type)
 * */
data class HomeState (
    val characters: List<Character> = emptyList(),
    val episodes: List<Episode> = emptyList(),
    val locations: List<Location> = emptyList(),
    val isNotFoundCharacterDataVisible: Boolean = false,
    val isNotFoundEpisodeDataVisible: Boolean = false,
    val isNotFoundLocationDataVisible: Boolean = false,
    val isRefreshing: Boolean = false,
    val isScrollingUp: Boolean = false,
    val isScrollUpButtonVisible: Boolean = false,
)