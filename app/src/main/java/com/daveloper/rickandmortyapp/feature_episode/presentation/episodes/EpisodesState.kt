package com.daveloper.rickandmortyapp.feature_episode.presentation.episodes

import com.daveloper.rickandmortyapp.core.domain.model.ItemDataFilter
import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode

/**The [EpisodesState] describes all [EpisodesViewModel] view/screen values.
 *
 * @param isFilterResumeVisible ([Boolean] type)
 * @param isFilterSelectorVisible ([Boolean] type)
 * @param episodes ([List]<[Character]> type)
 * @param season ([List]<[ItemDataFilter]> type)
 * @param selectedSeason ([String] type)
 * @param isLoading ([Boolean] type)
 * @param isNotFoundDataVisible ([Boolean] type)
 * @param isRefreshing ([Boolean] type)
 * @param isScrollingUp ([Boolean] type)
 * @param isScrollUpButtonVisible ([Boolean] type)
 * */
data class EpisodesState(
    val isFilterResumeVisible: Boolean = true,
    val isFilterSelectorVisible: Boolean = false,
    val episodes: List<Episode> = emptyList(),
    val season: List<ItemDataFilter> = emptyList(),
    val selectedSeason: String = Constants.EMPTY_STR,
    val isLoading: Boolean = false,
    val isNotFoundDataVisible: Boolean = false,
    val isRefreshing: Boolean = false,
    val isScrollingUp: Boolean = false,
    val isScrollUpButtonVisible: Boolean = false,
)
